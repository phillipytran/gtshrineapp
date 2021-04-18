package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.*

class ProductGridFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment with the ProductGrid theme
        val view = inflater.inflate(R.layout.shr_product_grid_fragment, container, false)

        // Set up the toolbar.
        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)

        // Set up the RecyclerView
        view.recycler_view.setHasFixedSize(true)
        view.recycler_view.layoutManager = GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        val adapter = ProductCardRecyclerViewAdapter(
                ProductEntry.initProductEntryList(resources))
        view.recycler_view.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small)
        view.recycler_view.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))

        return view;
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }
}

/*
// Set cut corner background for API 23+
//view.product_grid.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)


view.home_switcher.setOnClickListener{
    //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
    Timber.d("FUCK")
    (activity as NavigationHost).navigateTo(AdmittanceCalibration(),false)
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
view.command_mode_switcher.setOnClickListener{
    //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
    Timber.d("FUCK")
    (activity as NavigationHost).navigateTo(AdmittanceCalibration(),false)
}
return view
}

override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
    Timber.d("Hello")
    menuInflater.inflate(R.menu.shr_toolbar_menu, menu)
    super.onCreateOptionsMenu(menu, menuInflater)
}
}
*/