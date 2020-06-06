package com.e.truehomemobile.adapters.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.e.truehomemobile.R
import com.e.truehomemobile.adapters.page.ImagesViewPagerAdapter
import com.e.truehomemobile.models.apartment.ApartmentWithImages
import com.e.truehomemobile.viewHolders.ApartmentDetailsViewHolder
import kotlinx.android.synthetic.main.apartment_show_details.view.*

class ApartmentDetailsAdapter(val apartment: ApartmentWithImages) :
    RecyclerView.Adapter<ApartmentDetailsViewHolder>() {
    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.apartment_show_details, parent, false)
        return ApartmentDetailsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ApartmentDetailsViewHolder, position: Int) {
        holder.view.apartment_details_name.text = apartment.apartmentPartialResult.apartmentName
        holder.view.apartment_details_city.text = apartment.apartmentPartialResult.apartmentCity + ", " +
                apartment.apartmentPartialResult.apartmentZipCode
        holder.view.apartment_details_address.text =
            apartment.apartmentPartialResult.apartmentStreet + " " + apartment.apartmentPartialResult.apartmentStreetNumber
        holder.view.apartment_details_price.text = apartment.apartmentPartialResult.apartmentPrice.toString() + " zł"
        holder.view.apartment_details_description.text = apartment.apartmentPartialResult.apartmentDescription
//        if(apartment.apartmentPartialResult.user.telephoneNumber != null){
//            holder.view.apartment_details_phone.text = apartment.apartmentPartialResult.user.telephoneNumber
//        }

        var adapter: PagerAdapter = ImagesViewPagerAdapter(
            holder.view.context,
            apartment.apartmentPartialResult.apartmentImages
        )
        holder.view.apartment_details_view_pager.adapter = adapter

        holder.view.contact_button.setOnClickListener {
            holder.view.contact_mail.text = apartment.user.emailAddress
            holder.view.contact_phone.text = apartment.user.telephoneNumber
            holder.view.contact_card_view.visibility = View.VISIBLE
            holder.view.contact_button.visibility = View.GONE
        }
    }
}