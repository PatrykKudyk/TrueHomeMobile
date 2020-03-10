package com.e.truehomemobile.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e.truehomemobile.ActivityHolders.LogicHolders.LoginLogicHolder
import com.e.truehomemobile.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginLogicHolder = LoginLogicHolder(this, this)
        loginLogicHolder.initActivity()

        register_button.setOnClickListener {
           loginLogicHolder.handleRegisterButton()
        }

        login_button.setOnClickListener{
            loginLogicHolder.handleLoginButton()
        }

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
