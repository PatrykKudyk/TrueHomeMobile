package com.e.truehomemobile.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val loginEditText = this.findViewById(R.id.login_field) as EditText
        val passwordEditText = this.findViewById(R.id.password_field) as EditText

        val loginButton = this.findViewById(R.id.login_button) as Button


        startAnimations()
        initFonts()

        register_button.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivityForResult(intent, 0)
            clearFields()
        }

        loginButton.setOnClickListener{
            clearFieldsErrors()
            if(areFieldsFilled()){
                loginEditText.text.clear()
                passwordEditText.text.clear()
            }
        }

    }

    private fun clearFields() {
        clearFieldsErrors()
        login_field.text = null
        password_field.text = null
    }

    private fun clearFieldsErrors() {
        login_field_layout.error = null
        login_field.clearFocus()
        password_field_layout.error = null
        password_field.clearFocus()
    }

    private fun areFieldsFilled(): Boolean{
        var isCorrect = false
        if(login_field.text.toString() == ""){
            login_field_layout.error = getString(getStringIdentifier(this, "field_error_empty_field"))
        }else{
            isCorrect = true
        }
        if(password_field.text.toString() == "") {
            password_field_layout.error = getString(getStringIdentifier(this, "field_error_empty_field"))
            isCorrect = false
        }
        return isCorrect
    }

    private fun initFonts() {
        val password_field_layout : TextInputLayout = findViewById(R.id.password_field_layout)
        val typeface = ResourcesCompat.getFont(this, R.font.josefinsansregular)
        password_field_layout.typeface = typeface
    }

    private fun startAnimations() {
        val topToBottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)
        var bottomToTop = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)

        bottomToTop.startOffset = 300

        logoImageView.startAnimation(topToBottom)
        login_field_layout.startAnimation(topToBottom)
        password_field_layout.startAnimation(topToBottom)
        login_button.startAnimation(bottomToTop)
        register_button.startAnimation(bottomToTop)
    }

    private fun getStringIdentifier(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "string", context.packageName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                val login = data?.getStringExtra("login")
                login_field.setText(login)
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {

    }
}
