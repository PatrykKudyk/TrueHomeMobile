package com.e.truehomemobile.Activities

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
        passwordFontSetting()

        register_button.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener{
            if(areFieldsFilled(loginEditText, passwordEditText)){
                loginEditText.text.clear()
                passwordEditText.text.clear()
            }else{
                Toast.makeText(this,"Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun areFieldsFilled(login: EditText, password: EditText): Boolean{
        var isCorrect = false
        if(login.text.toString().equals("")){
            login.setError("Pole nie może być puste")
        }else{
            isCorrect = true
        }
        if(password.text.toString().equals("")) {
            password.setError("Pole nie może być puste")
            isCorrect = false
        }
        return isCorrect
    }

    private fun passwordFontSetting() {
        val password_field_layout : TextInputLayout = findViewById(R.id.password_field_layout)
        val typeface = ResourcesCompat.getFont(this, R.font.josefinsansregular)
        password_field_layout.setTypeface(typeface)
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

    override fun onBackPressed() {

    }
}
