package com.ecutbildning.marvelissimo.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.services.FireBaseAuth
import com.ecutbildning.marvelissimo.utilities.SnackbarManager
import kotlinx.android.synthetic.main.activity_login.*

class LogInActivity : AppCompatActivity() , View.OnClickListener {
    private lateinit var view: View
    private lateinit var snackbarManager: SnackbarManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        view = findViewById(android.R.id.content)
        snackbarManager = SnackbarManager()
        btnLogIn.setOnClickListener(this)
        login_register_button.setOnClickListener(this)
    }


    private fun signIn(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            FireBaseAuth.signIn(email, password, this, view)
        } else {
            snackbarManager.createSnackbar(view, resources.getString(R.string.signin_failed_missing_fields), Color.RED)

        }
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.btnLogIn -> {
                val intent = Intent(this@LogInActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.login_register_button -> signIn(login_email.text.toString(), login_password.text.toString())
        }
    }
}
