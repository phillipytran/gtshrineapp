package com.google.codelabs.mdc.kotlin.shrine


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import com.welie.blessed.*
import timber.log.Timber
import java.util.*


class BLEHandler private constructor(private val theContext: Context){
    companion object {
        private val ROBO_BLE_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E")
        private val ROBO_BLE_TXCHARACTERISTIC_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E")
        private val ROBO_BLE_RXCHARACTERISTIC_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E")
        private val MESSAGE_LENGTH = 13

        private var instance: BLEHandler? = null

        //when this is called from MainActivity, it initializes BLEHandler
        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): BLEHandler? {
            Timber.d("Inside BLEHandler getInstance()")
            if (instance == null) {
                Timber.d("BLEHandler instance is null")
                //initialize BLEHandler
                instance = BLEHandler(context)
                Timber.d("BLEHandler initialized")
            }
            Timber.d("Ready to return BLEHandler instance")
            return instance
        }
    }
    
    private lateinit var central: BluetoothCentralManager
    private var handler: Handler = Handler()
    private var macAddress: String? = null
    private var received: ArrayList<ByteArray> = arrayListOf()
    private var saveWorked = false
    var confirmSave = false
    var currentCommand = ""

    private var peripheralCallback: BluetoothPeripheralCallback = object : BluetoothPeripheralCallback() {
        override fun onServicesDiscovered(peripheral: BluetoothPeripheral) {
            Timber.i("Services discovered")

            peripheral.requestMtu(150) // increase MTU size to handle larger messages

            peripheral.requestConnectionPriority(ConnectionPriority.HIGH)

            if (peripheral.getService(ROBO_BLE_UUID) != null) {
                peripheral.getCharacteristic(
                        ROBO_BLE_UUID,
                        ROBO_BLE_RXCHARACTERISTIC_UUID
                )?.let { peripheral.setNotify(it, true) }
            }
        }

        override fun onNotificationStateUpdate(
                peripheral: BluetoothPeripheral,
                characteristic: BluetoothGattCharacteristic,
                status: GattStatus
        ) {
            if (status == GattStatus.SUCCESS) {
                if (peripheral.isNotifying(characteristic)) {
                    Timber.i("SUCCESS: Notifications on for <${characteristic.uuid}>")
                } else {
                    Timber.i("SUCCESS: Notifications off for <${characteristic.uuid}>")
                }
            } else {
                Timber.e("ERROR: Changing notification state failed for <${characteristic.uuid}>")
            }
        }

        override fun onCharacteristicWrite(
                peripheral: BluetoothPeripheral,
                value: ByteArray,
                characteristic: BluetoothGattCharacteristic,
                status: GattStatus
        ) {
            if (status == GattStatus.SUCCESS) {
                Timber.i("SUCCESS: Writing <${value.contentToString()}> to <${characteristic.uuid}>")
            } else {
                Timber.e("ERROR: Failed writing <${value.contentToString()}> to <${characteristic.uuid}>")
            }
        }

        override fun onCharacteristicUpdate(
                peripheral: BluetoothPeripheral,
                value: ByteArray,
                characteristic: BluetoothGattCharacteristic,
                status: GattStatus
        ) {
            if (status != GattStatus.SUCCESS) {
                Timber.d("GATT Connection Error")
                return
            }

            val characteristicUUID = characteristic.uuid

            if (characteristicUUID == ROBO_BLE_RXCHARACTERISTIC_UUID) {
                Timber.d("<$characteristicUUID> has been updated and contains: ${value.contentToString()}")
                updateReceived(value)
            } else {
                Timber.d("<$characteristicUUID> has been changed and contains: ${value.contentToString()}")
            }
        }

        override fun onMtuChanged(peripheral: BluetoothPeripheral, mtu: Int, status: GattStatus) {
            Timber.i("New MTU set: $mtu")
        }
    }

    private var centralCallback: BluetoothCentralManagerCallback = object : BluetoothCentralManagerCallback() {
        override fun onConnectedPeripheral(peripheral: BluetoothPeripheral) {
            Timber.i("Connected to <${peripheral.name}>")
            macAddress = peripheral.address
            Timber.d("MAC Address of peripheral is: $macAddress")
        }

        override fun onConnectionFailed(peripheral: BluetoothPeripheral, status: HciStatus) {
            Timber.e("Connection to <${peripheral.name}> failed with status: $status")
        }

        override fun onDisconnectedPeripheral(peripheral: BluetoothPeripheral, status: HciStatus) {
            Timber.i("Disconnected from <${peripheral.name}> with status: $status")

            handler.postDelayed({
                central.autoConnectPeripheral(peripheral, peripheralCallback)
            }, 5000)
        }

        override fun onDiscoveredPeripheral(
                peripheral: BluetoothPeripheral,
                scanResult: ScanResult
        ) {
            Timber.i("Found peripheral <${peripheral.name}>")
            central.stopScan()
            central.connectPeripheral(peripheral, peripheralCallback)
        }

        override fun onBluetoothAdapterStateChanged(state: Int) {
            Timber.i("Bluetooth adapter changed state to: $state")

            if (state == BluetoothAdapter.STATE_ON) {
                central.startPairingPopupHack()
                central.scanForPeripheralsWithServices(arrayOf(ROBO_BLE_UUID))
            }
        }

        override fun onScanFailed(scanFailure: ScanFailure) {
            Timber.i("Scanning failed with error $scanFailure")
        }
    }

    init {
        Timber.d("Inside BLEHandler initialization")
        central = BluetoothCentralManager(theContext, centralCallback, Handler())
        Timber.d("Created BluetoothCentral in BLEHandler initialization")
        central.startPairingPopupHack()
        central.scanForPeripheralsWithServices(arrayOf(ROBO_BLE_UUID))
    }

    private fun updateReceived(message: ByteArray) {
        Timber.d("Inside updatedReceived")
        if (message.size < MESSAGE_LENGTH) {
            Timber.e("Received message <${message.contentToString()}> is too short")
        } else {
            val formattedMessage = message.copyOfRange(0, MESSAGE_LENGTH)
            Timber.d("Formatted message is: <${formattedMessage.contentToString()}>")
            // for some reason Arduino appends 2 extra bytes to array (13 and 8) so these garbage values need to be removed before processing
            /*if (formattedMessage.last().toInt() == 8 && formattedMessage[formattedMessage.size - 2].toInt() == 13) {
                Timber.d("Removing last two indices of received array")
                formattedMessage = formattedMessage.copyOfRange(0,formattedMessage.size-2)
                Timber.d("New message:  <${formattedMessage.contentToString()}>")
            }*/
            saveWorked = if (formattedMessage.first().toInt() == 1 && formattedMessage.last().toInt() == 4) {
                Timber.d("Adding correctly formatted message <${formattedMessage.copyOfRange(1,formattedMessage.size-1).contentToString()}> to received array")
                received.add(0, formattedMessage.copyOfRange(1,formattedMessage.size-1))
                true
            } else {
                Timber.e("Message <${formattedMessage.contentToString()}> is incorrectly formatted")
                false
            }
        }
    }

    /**
     * call this function from Actuation
     * should be used exclusively for writing to characteristic
     */
    fun sendCommand(command: ByteArray) {
        Timber.d("Inside sendCommand with ByteArray: <${command.contentToString()}>")
        writeCharacteristic(command)
    }

//    /**
//     * call this function from Actuation
//     * and find the correct command and then write to BLE characteristic
//     */
//    fun sendCommand(result: Result) {
//        Timber.d("Inside sendCommand with Result <${result.resultText}>")
//        val nonConverted = Calibration.getInstance().calibration[result.resultText]
//        nonConverted?.let {
//            val converted = ByteArray(it.size)
//            for (i in it.indices) {
//                converted[i] = it[i].toByte()
//            }
//            Timber.d("Values in toSend ContentToString: <${converted.contentToString()}>")
//            writeCharacteristic(converted)
//        } ?: Timber.e("The retrieved ArrayList is null using key <${result.resultText}>")
//    }

    fun writeCharacteristic(command: ByteArray){
        macAddress?.let { mac ->
            val peripheral = central.getPeripheral(mac)
            Timber.d("MAC Address is valid")
            if (peripheral.getService(ROBO_BLE_UUID) != null) {
                Timber.d("Service is valid")
                peripheral.getCharacteristic(
                        ROBO_BLE_UUID,
                        ROBO_BLE_TXCHARACTERISTIC_UUID
                ).let { peri ->
                    Timber.d("Writing characteristic")
                    peri?.let {
                        peripheral.writeCharacteristic(it, command, WriteType.WITH_RESPONSE)
                    }
                }
            }
        }
    }

    fun getReceivedBytes() : ArrayList<ByteArray> {
        for (a in received) {
            Timber.d("The current ByteArray contains: <${a.contentToString()}>")
        }
        return received
    }

    fun getReceivedInts() : ArrayList<ArrayList<Int>> {
        val converted = arrayListOf<ArrayList<Int>>()
        if (saveWorked) {
            for (a in received) {
                val conversionStorage = arrayListOf<Int>()
                Timber.d("The current ByteArray contains: <${a.contentToString()}>")
                for (b in a) { // add values from microcontroller
                    conversionStorage.add(b.toInt())
                }
                // add formatting to communication protocol for sending command back to microcontroller -> 12/14/20 moved to Result class function
                // conversionStorage.add(0, 2)
                // conversionStorage.add(conversionStorage.size, 5)
                Timber.d("Successful conversion to ArrayList<Int>: <$conversionStorage>")
                converted.add(conversionStorage)
            }
        }
        return converted
    }
}