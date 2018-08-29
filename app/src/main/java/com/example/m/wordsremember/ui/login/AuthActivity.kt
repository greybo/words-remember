package com.example.m.wordsremember.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.m.wordsremember.R
import com.example.m.wordsremember.ui.MainActivity
import com.example.m.wordsremember.utils.Constants
import com.example.m.wordsremember.utils.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var model: AuthViewModel? = null
    private var isAnonymous: Boolean = false
    private lateinit var navController: NavController

    companion object {
        @JvmStatic
        fun start(context: Context, anotimous: Boolean) {
            val intent = Intent(context, AuthActivity::class.java)
            intent.putExtra(Constants.startSplash, anotimous)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mAuth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        intent?.also {
            isAnonymous = it.getBooleanExtra(Constants.startSplash, false)
        }

        if (isAnonymous) {
            anonymously()
        } else {
            navController.navigate(R.id.action_loading_fragment_to_login_fragment)
        }

        model = ViewModelProviders.of(this).get(AuthViewModel::class.java)

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

    private fun signUp(email: String, password: String) {
        if (!validation(email, password)) {
            Toast.makeText(this@AuthActivity, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Constants.TAG, "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        checkAuth(user)
                    } else {
                        Log.e(Constants.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@AuthActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }


    private fun signIn(email: String, password: String) {
        if (!validation(email, password)) {
            Toast.makeText(this@AuthActivity, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Constants.TAG, "signInWithEmail:success")
                        val user = mAuth.currentUser
                        checkAuth(user)
                    } else {
                        Log.e(Constants.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@AuthActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun hasSigned(): Boolean {
        var emailVerified = false
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            emailVerified = it.isEmailVerified
        }
        return emailVerified
    }

    private fun anonymously() {
        if (hasSigned()) {
            nextActivity()
        }

        mAuth.signInAnonymously()
                .addOnCompleteListener(this@AuthActivity) { task ->
                    if (task.isSuccessful) {
                        checkAuth(mAuth.currentUser)
                    } else {
                        Log.w(Constants.TAG, "signInAnonymously:failure", task.exception)
                        Toast.makeText(this@AuthActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun checkAuth(user: FirebaseUser?) {
        user?.uid?.let {
            Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
            nextActivity()
        }
    }

    private fun nextActivity() {
        MainActivity.start(this@AuthActivity)
        finish()
    }

    fun validation(email: String, password: String): Boolean {
        var res = false

        res = res && !email.isEmpty()
        res = res && !password.isEmpty()

        return res
    }

    override fun onBackPressed() {
        super.onBackPressed()
        nextActivity()
    }
}
