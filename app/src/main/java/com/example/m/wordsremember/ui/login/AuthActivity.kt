package com.example.m.wordsremember.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.m.wordsremember.R
import com.example.m.wordsremember.ui.MainActivity
import com.example.m.wordsremember.utils.Constants
import com.example.m.wordsremember.utils.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, AuthActivity::class.java))
        }
    }

    private lateinit var mAuth: FirebaseAuth

    private var model: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser
        nextActivity(currentUser)

        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        model?.getLogin()?.observe(this@AuthActivity, Observer { t ->
            Log.i(Constants.TAG, "getLogin: ${t?.email}")
            t?.let {
                signIn(it.email, it.password)

            }
        })
        model?.getSignUp()?.observe(this@AuthActivity, Observer { t ->
            Log.i(Constants.TAG, "getSignUp: ${t?.email}")
            t?.let {
                signUp(it.email, it.password)
            }
        })
    }

    fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Constants.TAG, "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        nextActivity(user)
                    } else {
                        Log.w(Constants.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@AuthActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Constants.TAG, "signInWithEmail:success")
                        val user = mAuth.currentUser
                        nextActivity(user)
                    } else {
                        Log.w(Constants.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@AuthActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun hasSigned(): Boolean {
        var emailVerified: Boolean = false
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl
            emailVerified = it.isEmailVerified
            val uid = it.uid
        }
        return emailVerified
    }

    private fun nextActivity(user: FirebaseUser?) {
        user?.uid?.let {
            Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
            MainActivity.start(this@AuthActivity)
        }
    }
}
