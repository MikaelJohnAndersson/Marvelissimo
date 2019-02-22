package com.ecutbildning.marvelissimo.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.services.FireBase
import com.ecutbildning.marvelissimo.utilities.SnackBarManager
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){

    private lateinit var view: View
    private lateinit var snackbarManager: SnackBarManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        view = findViewById(android.R.id.content)
        snackbarManager = SnackBarManager()

        register_button.setOnClickListener {
            createAccount(
                register_email.text.toString(),
                register_password.text.toString(),
                register_firstName.text.toString(),
                register_lastName.text.toString()
            )
        }
    }

    private fun createAccount(email: String, password: String, firstName: String, lastName: String) {
        if (email.isNotBlank() && password.isNotBlank() && firstName.isNotBlank() && lastName.isNotBlank()) {
            FireBase.createUser(email, password, firstName, lastName, this, view)
        } else {
            snackbarManager.createSnackbar(view, resources.getString(R.string.fields_missing_registration), Color.YELLOW)
        }
    }
}
