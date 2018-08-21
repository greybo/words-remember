package com.example.m.wordsremember.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.provider.ContactsContract
import com.example.m.wordsremember.model.User


class LoginViewModel : ViewModel() {

    private var userData: MutableLiveData<User>? = null
    private var loginData: MutableLiveData<User>? = null
    private var signUpData: MutableLiveData<User>? = null

    fun getUser(): LiveData<User>? {
        if (userData == null) {
            userData = MutableLiveData()

        }
        return userData
    }

    fun getLogin(): LiveData<User>? {
        if (loginData == null) {
            loginData = MutableLiveData()

        }
        return loginData
    }

    fun getSignUp(): LiveData<User>? {
        if (signUpData == null) {
            signUpData = MutableLiveData()

        }
        return signUpData
    }

    fun setLogin(user: User) {
        loginData?.value = user
    }

    fun setSignUp(user: User) {
        signUpData?.value = user

    }

    fun loadUser(user: User) {
        userData?.value = user
    }
}