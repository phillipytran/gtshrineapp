package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.admittance_cal_screen.view.*
import kotlinx.android.synthetic.main.shr_login_fragment.*
import kotlinx.android.synthetic.main.shr_login_fragment.view.*
import timber.log.Timber

/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        // Snippet from "Navigate to the next Fragment" section goes here.

        val view = inflater.inflate(R.layout.shr_login_fragment, container, false)



        view.next_button.setOnClickListener {
            if (!isPasswordValid(password_edit_text.text!!)) {
                password_text_input.error = getString(R.string.shr_error_password)
            } else {
                password_text_input.error = null
                Timber.d("login success")
                (activity as NavigationHost).navigateTo(HomeScreen(),false)
            }
            Timber.d("Username: "+ view.username_text_input.text!!)
        }

        view.password_edit_text.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(password_edit_text.text!!)) {
                password_text_input.error = null
            }
            false
        }



        view.clear_button.setOnClickListener {
            username_text_input.text?.clear()
            password_edit_text.text?.clear()

        }





        return view
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 2
    }

    private fun findUser(text: Editable?): Boolean {
        return text != null && text.length >= 2
    }
}
