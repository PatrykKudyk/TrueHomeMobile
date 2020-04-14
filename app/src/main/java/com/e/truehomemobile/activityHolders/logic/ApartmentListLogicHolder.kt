package com.e.truehomemobile.activityHolders.logic

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.truehomemobile.models.classes.LogicHolder
import kotlinx.android.synthetic.main.activity_apartment_list_activitty.*

class ApartmentListLogicHolder(context: Context, activity: Activity) : LogicHolder(context,
    activity) {

    fun initActivity(){
        activity.apartment_list_recycler_view.layoutManager = LinearLayoutManager(context)

    }
}