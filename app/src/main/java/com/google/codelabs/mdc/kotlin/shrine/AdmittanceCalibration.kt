package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.command_screen.view.*
import kotlinx.android.synthetic.main.admittance_cal_screen.view.*
import kotlinx.android.synthetic.main.position_cal_screen.view.*
import kotlinx.android.synthetic.main.shr_backdrop.view.*
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.app_bar
import timber.log.Timber

class AdmittanceCalibration : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.admittance_cal_screen, container, false)

        (activity as AppCompatActivity).setSupportActionBar(view.adm_cal_app_bar)
        view.adm_cal_app_bar.setNavigationOnClickListener(NavigationIconClickListener(
                activity!!,
                view.grid_adm_cal,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.ic_gt_logo_3), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu)))


        val items = listOf("Index MCP", "Index PIP", "Middle", "Thumb CMC", "Thumb")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        view.textField_adm.setAdapter(adapter)

        view.textField_adm.setOnItemClickListener { adapterView, view, i, l ->
            Timber.d("Selected item is: ${items[i]}")
        }

        view.toggle_button_adm.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                Timber.d("Checked")
                view.command_text_adm.setText(R.string.checked)
            } else {
                Timber.d("Unchecked")
                view.command_text_adm.text = getText(R.string.unchecked)
            }
        }

        view.up_button_adm.setOnClickListener{
            Timber.d("Up button pressed")
        }
        view.down_button_adm.setOnClickListener {
            Timber.d("Close button pressed")
        }
        view.transfer_button_adm.setOnClickListener{
            Timber.d("Transfer button pressed")
        }
        view.stop_Button_adm.setOnClickListener{
            Timber.d("Stop button pressed")
        }

        val snackBarView = view.snackbar_view_adm
        view.save_button_adm.setOnClickListener {
            view.save_progress_adm.visibility = View.VISIBLE
            val saveHandler = Handler(Looper.getMainLooper())
            saveHandler.postDelayed({
                Snackbar.make(snackBarView, "Saved", Snackbar.LENGTH_SHORT)
                        .setAction("Dismiss") {

                        }
                        .show()
                view.save_progress_adm.visibility = View.INVISIBLE
            }, 3000)
        }

        view.home_switcher.setOnClickListener{
            //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
            Timber.d("Navigating Home")
            (activity as NavigationHost).navigateTo(HomeScreen(),false)
        }
        view.pos_cal_switcher.setOnClickListener{
            //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
            Timber.d("Navigating to Position Calibration")
            (activity as NavigationHost).navigateTo(PositionCalibration(),false)
        }
        /*view.adm_cal_switcher.setOnClickListener{
            //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
            Timber.d("Navigating to Admittance Calibration")
            (activity as NavigationHost).navigateTo(FeaturedFragment(),false)
        }*/
        view.command_mode_switcher.setOnClickListener{
            //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
            Timber.d("Navigating to Command Screen")
            (activity as NavigationHost).navigateTo(CommandMode(),false)
        }

        return view
    }

    //override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
    //    Timber.d("Hello")
    //    menuInflater.inflate(R.menu.shr_toolbar_menu, menu)
    //   super.onCreateOptionsMenu(menu, menuInflater)
    //}

}