package com.e.truehomemobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.truehomemobile.MyApp
import com.e.truehomemobile.R
import com.e.truehomemobile.models.apartment.Apartment
import com.e.truehomemobile.viewHolders.ApartmentListViewHolder


//val apartmentList: Array<Apartment>
class ApartmentListAdapter(): RecyclerView.Adapter<ApartmentListViewHolder>() {
    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ApartmentListViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentListViewHolder {
        val laoutInflater = LayoutInflater.from(parent.context)
        if(MyApp.isLogged){
            val cellForRow = laoutInflater.inflate(R.layout.apartment_cell_logged, parent, false)
            return ApartmentListViewHolder(cellForRow)
        } else {
            val cellForRow = laoutInflater.inflate(R.layout.apartment_cell_not_logged, parent, false)
            return ApartmentListViewHolder(cellForRow)
        }
    }
}