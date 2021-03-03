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
import kotlinx.android.synthetic.main.fragment_featured.view.*
import kotlinx.android.synthetic.main.fragment_featured.view.command_text
import kotlinx.android.synthetic.main.fragment_featured.view.down_button
import kotlinx.android.synthetic.main.fragment_featured.view.grid
import kotlinx.android.synthetic.main.fragment_featured.view.save_button
import kotlinx.android.synthetic.main.fragment_featured.view.save_progress
import kotlinx.android.synthetic.main.fragment_featured.view.snackbar_view
import kotlinx.android.synthetic.main.fragment_featured.view.stop_Button
import kotlinx.android.synthetic.main.fragment_featured.view.toggle_button
import kotlinx.android.synthetic.main.fragment_featured.view.up_button
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.app_bar
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

        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)
        view.app_bar.setNavigationOnClickListener(NavigationIconClickListener(
                activity!!,
                view.grid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.ic_gt_logo_2), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu)))



        view.toggle_button.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                Timber.d("Checked")
                view.command_text.setText(R.string.checked)
            } else {
                Timber.d("Unchecked")
                view.command_text.text = getText(R.string.unchecked)
            }
        }



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
        }

        return view
    }

    //override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
    //    Timber.d("Hello")
    //    menuInflater.inflate(R.menu.shr_toolbar_menu, menu)
    //   super.onCreateOptionsMenu(menu, menuInflater)
    //}

}