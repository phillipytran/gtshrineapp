package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.command_screen.view.*
import kotlinx.android.synthetic.main.admittance_cal_screen.view.*
import kotlinx.android.synthetic.main.admittance_cal_screen.view.command_text_adm
import kotlinx.android.synthetic.main.admittance_cal_screen.view.down_button_adm
import kotlinx.android.synthetic.main.admittance_cal_screen.view.grid_adm_cal
import kotlinx.android.synthetic.main.admittance_cal_screen.view.save_button_adm
import kotlinx.android.synthetic.main.admittance_cal_screen.view.save_progress_adm
import kotlinx.android.synthetic.main.admittance_cal_screen.view.snackbar_view_adm
import kotlinx.android.synthetic.main.admittance_cal_screen.view.stop_Button_adm
import kotlinx.android.synthetic.main.admittance_cal_screen.view.toggle_button_adm
import kotlinx.android.synthetic.main.admittance_cal_screen.view.up_button_adm
import kotlinx.android.synthetic.main.shr_backdrop.view.*
import timber.log.Timber

class CommandMode : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.command_screen, container, false)

        (activity as AppCompatActivity).setSupportActionBar(view.command_mode_app_bar)
        view.command_mode_app_bar.setNavigationOnClickListener(NavigationIconClickListener(
                activity!!,
                view.grid_command_mode,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.ic_gt_logo_3), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu)))



        view.toggle_button_com.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                Timber.d("Checked")
                view.command_text_com.setText(R.string.checked)
            } else {
                Timber.d("Unchecked")
                view.command_text_com.text = getText(R.string.unchecked)
            }
        }

        view.up_button_com.setOnClickListener{
            Timber.d("Up button pressed")
        }
        view.down_button_com.setOnClickListener {
            Timber.d("Close button pressed")
        }
        view.stop_Button_com.setOnClickListener{
            Timber.d("Stop button pressed")
        }
        view.home_pos_button_com.setOnClickListener{
            Timber.d("Home position button pressed")
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
        view.adm_cal_switcher.setOnClickListener{
            //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
            Timber.d("Navigating to Admittance Calibration")
            (activity as NavigationHost).navigateTo(AdmittanceCalibration(),false)
        }
        /*view.command_mode_switcher.setOnClickListener{
            //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
            Timber.d("Navigating to Command Screen")
            (activity as NavigationHost).navigateTo(CommandMode(),false)
        }*/

        return view
    }

    //override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
    //    Timber.d("Hello")
    //    menuInflater.inflate(R.menu.shr_toolbar_menu, menu)
    //   super.onCreateOptionsMenu(menu, menuInflater)
    //}

}