package com.e.truehomemobile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.logic.RegistrationLogicHolder
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val logicHolder = RegistrationLogicHolder(this, this)
        logicHolder.initActivity()

        register_button.setOnClickListener {
            logicHolder.handleRegisterButton()
        }

        backTextView.setOnClickListener{
            finish()
        }

        logicHolder.handleFieldsListeners()
    }


    override fun onBackPressed() {

    }
}
