package com.example.m.wordsremember.ui.login

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.arch.lifecycle.ViewModelProviders
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.m.wordsremember.R
import com.example.m.wordsremember.model.User
import com.example.m.wordsremember.utils.Constants.TAG
import com.example.m.wordsremember.utils.LoginViewModel
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*


class LoginFragment : Fragment() {

    private var model: LoginViewModel? = null
//    private val btnLogIn by lazy { findViewById<Button>(R.id.button_login) }
//    private val btnSignUp by lazy { findViewById<Button>(R.id.button_signUp) }
//    private val emailText by lazy { findViewById<TextInputEditText>(R.id.text_email) }
//    private val passwordText by lazy { findViewById<TextInputEditText>(R.id.text_password) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        val emailText = view.findViewById<EditText>(R.id.text_email)
        val passwordText = view.findViewById<EditText>(R.id.text_password)

        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        model?.getUser()?.observe(this@LoginFragment, Observer { t ->
            Log.i(TAG, "login activity: ${t?.email}")
        })

        button_login.setOnClickListener {
            model?.setLogin(User().apply {
                email = emailText.text.toString()
                password = passwordText.text.toString()

            })
        }
        return view
    }


}


