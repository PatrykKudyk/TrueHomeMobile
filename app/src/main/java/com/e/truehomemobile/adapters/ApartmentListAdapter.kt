package com.e.truehomemobile.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.truehomemobile.MyApp
import com.e.truehomemobile.R
import com.e.truehomemobile.models.apartment.Apartment
import com.e.truehomemobile.viewHolders.ApartmentListViewHolder
import kotlinx.android.synthetic.main.apartment_cell_not_logged.view.*


//val apartmentList: Array<Apartment>
class ApartmentListAdapter(private val apartmentList: ArrayList<Apartment>): RecyclerView.Adapter<ApartmentListViewHolder>() {
    override fun getItemCount(): Int {
        return apartmentList.size
    }

    override fun onBindViewHolder(holder: ApartmentListViewHolder, position: Int) {

        holder.view.apartment_name_text_view.text = apartmentList[position].apartmentName
        holder.view.apartment_address_text_view.text = apartmentList[position].apartmentCity + ", " +
                apartmentList[position].apartmentStreet + " " + apartmentList[position].apartmentStreetNumber +
                "/" + apartmentList[position].apartmentNumber
        holder.view.apartment_price_text_view.text = apartmentList[position].apartmentPrice.toString() + " zł"
        holder.view.description_text_view.text = apartmentList[position].apartmentDescription

        val mainCardView = holder.view.apartment_not_logged_cell_card_view
        val descriptionCardView = holder.view.description_card_view

        mainCardView.setOnClickListener{
            if(descriptionCardView.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(mainCardView, AutoTransition())
                descriptionCardView.visibility = View.VISIBLE
            }else{
                TransitionManager.beginDelayedTransition(mainCardView, AutoTransition())
                descriptionCardView.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if(MyApp.isLogged){
            val cellForRow = layoutInflater.inflate(R.layout.apartment_cell_logged, parent, false)
            return ApartmentListViewHolder(cellForRow)
        } else {
            val cellForRow = layoutInflater.inflate(R.layout.apartment_cell_not_logged, parent, false)
            return ApartmentListViewHolder(cellForRow)
        }
    }
}