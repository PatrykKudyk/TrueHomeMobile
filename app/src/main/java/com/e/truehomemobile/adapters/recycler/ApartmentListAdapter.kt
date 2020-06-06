package com.e.truehomemobile.adapters.recycler

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.truehomemobile.MyApp
import com.e.truehomemobile.R
import com.e.truehomemobile.activities.MainActivity
import com.e.truehomemobile.fragments.apartment.ApartmentDetailsFragment
import com.e.truehomemobile.models.apartment.Apartment
import com.e.truehomemobile.viewHolders.ApartmentListViewHolder
import kotlinx.android.synthetic.main.apartment_cell_logged.view.*
import kotlinx.android.synthetic.main.apartment_cell_not_logged.view.*

class ApartmentListAdapter(var apartmentList: ArrayList<Apartment>) :
    RecyclerView.Adapter<ApartmentListViewHolder>() {
    override fun getItemCount(): Int {
        return apartmentList.size
    }


    override fun onBindViewHolder(holder: ApartmentListViewHolder, position: Int) {
        holder.view.apartment_name_text_view_logged.text = apartmentList[position].apartmentPartialResult.apartmentName
        holder.view.apartment_address_text_view_logged.text =
            apartmentList[position].apartmentPartialResult.apartmentCity + ", " +
                    apartmentList[position].apartmentPartialResult.apartmentStreet + " " + apartmentList[position].apartmentPartialResult.apartmentStreetNumber
        holder.view.apartment_zip_code_text_view_logged.text =
            apartmentList[position].apartmentPartialResult.apartmentZipCode
        holder.view.apartment_price_text_view_logged.text =
            apartmentList[position].apartmentPartialResult.apartmentPrice.toString() + " zł"
        holder.view.description_text_view_logged.text = apartmentList[position].apartmentPartialResult.apartmentDescription

        if (apartmentList[position].apartmentPartialResult.apartmentImage != null) {
            val string = apartmentList[position].apartmentPartialResult.apartmentImage.substring(21)
            val imageBytes = Base64.decode(string, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            if (decodedImage != null) {
                holder.view.image_main_apartment_logged.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        decodedImage, 130, 130, false
                    )
                )
            } else {
                holder.view.image_main_apartment_logged.setImageBitmap(decodedImage)
            }

        } else {
            holder.view.image_main_apartment_logged.setImageResource(R.drawable.icon)
        }

        val mainCardView = holder.view.apartment_logged_cell_card_view_logged

        mainCardView.setOnClickListener {
            val apartmentDetailsFragment = ApartmentDetailsFragment.newInstance(
                apartmentList[position].apartmentPartialResult.apartmentId
            )
            val manager = (holder.itemView.context as MainActivity).supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.frame_layout, apartmentDetailsFragment)
                ?.addToBackStack(ApartmentDetailsFragment.toString())
                ?.commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = layoutInflater.inflate(R.layout.apartment_cell_logged, parent, false)
            return ApartmentListViewHolder(cellForRow)
    }

}