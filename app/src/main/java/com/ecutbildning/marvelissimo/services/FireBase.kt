package com.ecutbildning.marvelissimo.services

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import com.ecutbildning.marvelissimo.activities.LogInActivity
import com.ecutbildning.marvelissimo.activities.MainActivity
import com.ecutbildning.marvelissimo.dtos.User
import com.ecutbildning.marvelissimo.utilities.SnackBarManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FireBase {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var snackBarManager: SnackBarManager = SnackBarManager()
    var currentUser: User? = null

    fun signIn(email: String, password: String, context: Context, view: View) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    val currentUserUid = auth.currentUser?.uid
                    if (currentUserUid != null){
                        FirebaseFirestore.getInstance().collection("users")
                            .document(currentUserUid)
                            .get()
                            .addOnSuccessListener { documentSnapshot  ->
                                currentUser = documentSnapshot.toObject(User::class.java)
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)
                            }
                    }
                } else {
                    snackBarManager.createSnackbar(view, "Wrong credentials", Color.RED)
                }
            }
    }

    fun createUser(email: String, password: String, firstName: String, lastName: String, context: Context, view: View) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    writeNewUser(firstName, lastName, email)
                    val intent = Intent(context, LogInActivity::class.java)
                    context.startActivity(intent)
                } else {
                    snackBarManager.createSnackbar(view, "Registration failed", Color.RED)
                }
            }
    }

    private fun writeNewUser(firstName: String, lastName: String, email: String) {
        val newUser = User(email, firstName, lastName, true)
        val database = FirebaseFirestore.getInstance()
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null)
            database.collection("users").document(currentUserUid).set(newUser)
    }

    fun signOut () {
        currentUser?.loggedIn=false
        auth.signOut()
    }

}