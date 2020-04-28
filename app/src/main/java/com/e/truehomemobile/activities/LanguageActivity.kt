package com.e.truehomemobile.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.logic.LanguageLogicHolder
import com.e.truehomemobile.models.classes.ApplicationLanguageHelper

class LanguageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        val logicHolder = LanguageLogicHolder(this, this)
        logicHolder.initActivity()
    }



}
