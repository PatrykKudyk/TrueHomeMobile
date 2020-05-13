package com.e.truehomemobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.truehomemobile.R
import com.e.truehomemobile.models.apartment.ApartmentWithImages
import com.e.truehomemobile.models.classes.MarginItemDecorationHorizontal
import com.e.truehomemobile.viewHolders.ApartmentDetailsViewHolder
import kotlinx.android.synthetic.main.apartment_details.view.*

class ApartmentDetailsAdapter(val apartment: ApartmentWithImages): RecyclerView.Adapter<ApartmentDetailsViewHolder>() {
    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.apartment_details, parent, false)
        return ApartmentDetailsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ApartmentDetailsViewHolder, position: Int) {
        holder.view.apartment_details_name.text = apartment.apartmentName
        holder.view.apartment_details_address.text = apartment.apartmentCity + ", " +
                apartment.apartmentStreet + " " + apartment.apartmentStreetNumber
        holder.view.apartment_details_zip_code.text = apartment.apartmentZipCode
        holder.view.apartment_details_price.text = apartment.apartmentPrice.toString()
        holder.view.apartment_details_description.text = apartment.apartmentDescription
        if(apartment.apartmentImages != null){
            val recyclerView: RecyclerView = holder.view.images_recycler_view
            val mLayoutManager: LinearLayoutManager = LinearLayoutManager(holder.view.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = mLayoutManager
            recyclerView.addItemDecoration(
                MarginItemDecorationHorizontal(
                    12
                )
            )
            recyclerView.adapter = ApartmentImagesAdapter(apartment.apartmentImages)
        }
    }
}