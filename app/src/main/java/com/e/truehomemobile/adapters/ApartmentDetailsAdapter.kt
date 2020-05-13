package com.e.truehomemobile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.e.truehomemobile.R
import com.e.truehomemobile.models.apartment.ApartmentWithImages
import com.e.truehomemobile.models.classes.MarginItemDecorationHorizontal
import com.e.truehomemobile.viewHolders.ApartmentDetailsViewHolder
import kotlinx.android.synthetic.main.apartment_show_details.view.*

class ApartmentDetailsAdapter(val apartment: ApartmentWithImages): RecyclerView.Adapter<ApartmentDetailsViewHolder>() {
    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.apartment_show_details, parent, false)
        return ApartmentDetailsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ApartmentDetailsViewHolder, position: Int) {
        holder.view.apartment_details_name.text = apartment.apartmentName
        holder.view.apartment_details_city.text = apartment.apartmentCity + ", " +
                apartment.apartmentZipCode
        holder.view.apartment_details_address.text = apartment.apartmentStreet + " " + apartment.apartmentStreetNumber
        holder.view.apartment_details_price.text = apartment.apartmentPrice.toString() + " zł"
        holder.view.apartment_details_description.text = apartment.apartmentDescription

        var adapter: PagerAdapter = ImagesViewPagerAdapter(holder.view.context, apartment.apartmentImages)
        holder.view.apartment_details_view_pager.adapter = adapter
    }
}