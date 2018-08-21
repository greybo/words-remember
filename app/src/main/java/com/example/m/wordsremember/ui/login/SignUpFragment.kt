package com.example.m.wordsremember.ui.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m.wordsremember.R
import com.example.m.wordsremember.utils.LoginViewModel

class SignUpFragment : Fragment() {

    private var model: LoginViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.sign_up_fragment, container, false)

        return view
    }
}
