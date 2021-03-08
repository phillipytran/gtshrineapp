package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.admittance_cal_screen.view.*
import kotlinx.android.synthetic.main.admittance_cal_screen.view.command_text_adm
import kotlinx.android.synthetic.main.admittance_cal_screen.view.toggle_button_adm
import kotlinx.android.synthetic.main.home_screen.view.*
import kotlinx.android.synthetic.main.shr_backdrop.view.*
import timber.log.Timber

class HomeScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.home_screen, container, false)

        (activity as AppCompatActivity).setSupportActionBar(view.home_app_bar)
        view.home_app_bar.setNavigationOnClickListener(NavigationIconClickListener(
                activity!!,
                view.grid_home,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.ic_gt_logo_3), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu)))


        view.sign_out_button.setOnClickListener {
            Timber.d("Navigating to Login Screen")
            (activity as NavigationHost).navigateTo(LoginFragment(),false)

        }


        view.toggle_button_home.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                Timber.d("Checked")
                view.command_text_home.setText(R.string.checked)
            } else {
                Timber.d("Unchecked")
                view.command_text_home.text = getText(R.string.unchecked)
            }
        }


        /*
        val snackBarView = view.snackbar_view
        view.save_button.setOnClickListener {
            view.save_progress.visibility = View.VISIBLE
            val saveHandler = Handler(Looper.getMainLooper())
            saveHandler.postDelayed({
                Snackbar.make(snackBarView, "Saved", Snackbar.LENGTH_SHORT)
                        .setAction("Dismiss") {

                        }
                        .show()
                view.save_progress.visibility = View.INVISIBLE
            }, 3000)
        }*/

        /*view.home_switcher.setOnClickListener{
            //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
            Timber.d("Navigating Home")
            (activity as NavigationHost).navigateTo(HomeScreen(),false)
        }*/
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