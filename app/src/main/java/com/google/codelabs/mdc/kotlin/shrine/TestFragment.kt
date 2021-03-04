package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_featured.view.grid
import kotlinx.android.synthetic.main.fragment_test.view.*
import kotlinx.android.synthetic.main.shr_backdrop.view.*
import timber.log.Timber

class TestFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Timber.d("Hello there")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_test, container, false)

        (activity as AppCompatActivity).setSupportActionBar(view.app_bar_test)
        view.app_bar_test.setNavigationOnClickListener(NavigationIconClickListener(
                activity!!,
                view.grid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.shr_branded_menu), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu)))

        view.home_switcher.setOnClickListener {
            Timber.d("FUCK4")
            (activity as NavigationHost).navigateTo(FeaturedFragment(),false)
        }

        view.adm_cal_switcher.setOnClickListener {
            Timber.d("FUCK6")
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