package com.e.truehomemobile.activityHolders.logic

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.truehomemobile.R
import com.e.truehomemobile.models.classes.MarginItemDecoration
import com.e.truehomemobile.adapters.ApartmentListAdapter
import com.e.truehomemobile.models.apartment.Apartment
import com.e.truehomemobile.models.classes.LogicHolder
import kotlinx.android.synthetic.main.activity_apartment_list_activitty.*
import kotlinx.android.synthetic.main.nav_header.*

class ApartmentListLogicHolder(context: Context, activity: Activity) : LogicHolder(context,
    activity) {

    fun initActivity(){
//        activity.apartment_list_recycler_view.layoutManager = LinearLayoutManager(context)
//        activity.apartment_list_recycler_view.addItemDecoration(
//            MarginItemDecoration(
//                12
//            )
//        )
//        activity.apartment_list_recycler_view.adapter = ApartmentListAdapter(initApartmentList())


    }


    private fun initApartmentList(): ArrayList<Apartment>{               // metoda testowa, bez bazy
        val apartment1 = Apartment(0, "Dobry apartment", "Wroclaw",
            "Bzowa", "21", "37a", "51-132",
            300,"Opis apartamentu, który jest właśnie opisywany bla bla" +
                    "bla bla bla")
        val apartmentList = ArrayList<Apartment>()
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)

        return apartmentList
    }
}





