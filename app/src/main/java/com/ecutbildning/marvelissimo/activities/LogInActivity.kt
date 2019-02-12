package com.ecutbildning.marvelissimo.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.services.FireBase
import com.ecutbildning.marvelissimo.utilities.SnackBarManager
import kotlinx.android.synthetic.main.activity_log_in2.*

class LogInActivity : AppCompatActivity() {

    private lateinit var view: View
    private lateinit var snackbarManager: SnackBarManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in2)
        view = findViewById(android.R.id.content)
        snackbarManager = SnackBarManager()

        btnLogIn.setOnClickListener {
            signIn(login_email.text.toString(), login_password.text.toString())
        }
        login_register_button.setOnClickListener {
            val intent = Intent(this@LogInActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            FireBase.signIn(email, password, this, view)

        } else {
            snackbarManager.createSnackbar(view, resources.getString(R.string.failed_missing_signIn), Color.RED)
        }
    }

}
