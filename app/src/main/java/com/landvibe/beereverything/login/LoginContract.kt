package com.landvibe.beereverything.login

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


interface LoginContract {
    interface View {

        var googleSignInClient: GoogleSignInClient
        var gso: GoogleSignInOptions
        var auth: FirebaseAuth

        fun init()
        fun signIn()
        fun signOut()
        fun checkLoginStatus()

    }
}

const val REQUEST_CODE = 100