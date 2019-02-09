package com.ecutbildning.marvelissimo.services

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.activities.MainActivity
import com.ecutbildning.marvelissimo.snackBar.SnackBarManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object FireBase {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private  var user: FirebaseUser? = null
    private var snackBarManager: SnackBarManager = SnackBarManager()


    fun signIn(email: String, password: String, context: Context, view: View) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {

                    user = auth.currentUser

                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                } else {
                    snackBarManager.createSnackbar(view, "wrong credentials", Color.RED)
                }

            }
    }

}