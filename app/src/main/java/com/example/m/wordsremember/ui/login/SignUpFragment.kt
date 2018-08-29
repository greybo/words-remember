package com.example.m.wordsremember.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.m.wordsremember.R
import com.example.m.wordsremember.model.User
import com.example.m.wordsremember.utils.AuthViewModel

class SignUpFragment : Fragment() {

    private var model: AuthViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sign_up_fragment, container, false)

        val emailText = view.findViewById<EditText>(R.id.text_email)
        val passwordText = view.findViewById<EditText>(R.id.text_password)
        val buttonSignUp = view.findViewById<Button>(R.id.button_signUp)
        activity?.also { activity ->
            model = ViewModelProviders.of(activity).get(AuthViewModel::class.java)

            buttonSignUp.setOnClickListener {
                model?.setSignUp(User().apply {
                    email = emailText.text.toString()
                    password = passwordText.text.toString()
                })
            }
        }

        return view
    }
}
