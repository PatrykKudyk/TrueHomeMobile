package com.e.truehomemobile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.logic.ApartmentListLogicHolder

class ApartmentListActivitty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apartment_list_activitty)

        val logicHolder = ApartmentListLogicHolder(this, this)
        logicHolder.initActivity()
    }
}
