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
import kotlinx.android.synthetic.main.shr_backdrop.view.*
import timber.log.Timber

class FeaturedFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_featured, container, false)

        (activity as AppCompatActivity).setSupportActionBar(view.app_bar_featured)
        view.app_bar_featured.setNavigationOnClickListener(NavigationIconClickListener(
                activity!!,
                view.grid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.shr_branded_menu), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu)))

        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        view.textField.setAdapter(adapter)

        view.textField.setOnItemClickListener { adapterView, view, i, l ->
            Timber.d("Selected item is: ${items[i]}")
        }

        view.toggle_button.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                Timber.d("Checked")
                view.command_text.setText(R.string.checked)
            } else {
                Timber.d("Unchecked")
                view.command_text.text = getText(R.string.unchecked)
            }
        }

        view.up_button.setOnClickListener{
            Timber.d("Up button pressed")
        }

        view.down_button.setOnClickListener {
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

        view.command_mode_switcher.setOnClickListener {
            Timber.d("FUCK3")
            (activity as NavigationHost).navigateTo(TestFragment(),false)
        }

        view.adm_cal_switcher.setOnClickListener {
            Timber.d("FUCK5")
            (activity as NavigationHost).navigateTo(ProductGridFragment(),false)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        Timber.d("Hello")
        menuInflater.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

}