package com.example.m.wordsremember.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.m.wordsremember.R
import com.example.m.wordsremember.model.User
import com.example.m.wordsremember.utils.AuthViewModel


class LoginFragment : Fragment() {

    private var model: AuthViewModel? = null
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        val emailText = view.findViewById<EditText>(R.id.text_email)
        val passwordText = view.findViewById<EditText>(R.id.text_password)
        val buttonLogin = view.findViewById<Button>(R.id.button_login)
        activity?.also { activity ->
            model = ViewModelProviders.of(activity).get(AuthViewModel::class.java)

            buttonLogin.setOnClickListener {
                model?.setLogin(User().apply {
                    email = emailText.text.toString()
                    password = passwordText.text.toString()

                })
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val signUpText = view.findViewById<TextView>(R.id.text_sign_up)
        signUpText.setOnClickListener {
            navController.navigate(R.id.action_login_fragment_to_sign_up_fragment)
        }
    }

}


