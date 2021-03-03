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
import kotlinx.android.synthetic.main.fragment_featured.view.*
import kotlinx.android.synthetic.main.fragment_featured.view.command_text
import kotlinx.android.synthetic.main.fragment_featured.view.grid
import kotlinx.android.synthetic.main.fragment_featured.view.save_button
import kotlinx.android.synthetic.main.fragment_featured.view.save_progress
import kotlinx.android.synthetic.main.fragment_featured.view.snackbar_view
import kotlinx.android.synthetic.main.fragment_featured.view.stop_Button
import kotlinx.android.synthetic.main.fragment_featured.view.textField
import kotlinx.android.synthetic.main.fragment_featured.view.toggle_button
import kotlinx.android.synthetic.main.fragment_featured_position.view.*
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.app_bar
import timber.log.Timber

class PositionCalibration : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_featured_position, container, false)

        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)
        view.app_bar.setNavigationOnClickListener(NavigationIconClickListener(
                activity!!,
                view.grid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.ic_gt_logo_2), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu)))

        val items = listOf("Index MCP", "Index PIP", "Middle", "Thumb CMC", "Thumb MCP", "Index Ext", "Middle Ext", "Thumb CMC Ext", "Thumb Ext")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        view.textField.setAdapter(adapter)

        view.textField.setOnItemClickListener { adapterView, view, i, l ->
            Timber.d("Selected item is: ${items[i]}")
        }

        view.toggle_button.addOnButtonCheckedListener {group, checkedId, isChecked ->
            if (isChecked) {
                Timber.d("Checked")
                view.command_text.setText(R.string.checked)
            } else {
                Timber.d("Unchecked")
                view.command_text.text = getText(R.string.unchecked)
            }
        }

        view.stop_Button.setOnClickListener{
            Timber.d("Stop button pressed")
        }
        view.open_button.setOnClickListener{
            Timber.d("Open button pressed")
        }
        view.close_button.setOnClickListener{
            Timber.d("Close button pressed")
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
      //  Timber.d("Hello")
        //menuInflater.inflate(R.menu.shr_toolbar_menu, menu)
        //super.onCreateOptionsMenu(menu, menuInflater)
    //}

}