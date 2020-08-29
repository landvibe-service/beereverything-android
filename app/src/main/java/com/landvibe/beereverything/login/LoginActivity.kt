package com.landvibe.beereverything.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.beerlist.BeerListActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity :
        LoginContract.View, AppCompatActivity(R.layout.activity_login), View.OnClickListener {


    override lateinit var googleSignInClient: GoogleSignInClient
    override lateinit var gso: GoogleSignInOptions
    override lateinit var auth: FirebaseAuth
    private var loginStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        googleSignInButton.setOnClickListener(this)
        googleSignOutButton.setOnClickListener(this)
    }

    override fun init() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
    }

    override fun signIn() {
        googleSignInClient.signInIntent.also {
            startActivityForResult(it, REQUEST_CODE)
        }
    }

    override fun signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener {
            Toast.makeText(this, "Sign Out Success!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun checkLoginStatus() {
        auth.currentUser.also {
            loginStatus = when (it) {
                null -> false
                else -> true
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.googleSignInButton -> {
                signIn()
            }
            R.id.googleSignOutButton -> {
                checkLoginStatus()
                when (loginStatus) {
                    true -> signOut()
                    else -> Toast.makeText(this, "Sign Out Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                if (result.isSuccess) {
                    val account = result.signInAccount
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)

                    Toast.makeText(this, "Sign In Success!!", Toast.LENGTH_SHORT).show()
                    //TODO : MainActivity 가 아닌 다른 Activity 로 넘어가게 해야함 -- 종신
                    Intent(this, BeerListActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
        }
    }

}