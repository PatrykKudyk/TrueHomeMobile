package com.e.truehomemobile.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.truehomemobile.MyApp
import com.e.truehomemobile.R
import com.e.truehomemobile.activities.MainActivity
import com.e.truehomemobile.fragments.Apartment.ApartmentDetailsFragment
import com.e.truehomemobile.models.apartment.Apartment
import com.e.truehomemobile.viewHolders.ApartmentListViewHolder
import kotlinx.android.synthetic.main.apartment_cell_logged.view.*
import kotlinx.android.synthetic.main.apartment_cell_not_logged.view.*

class ApartmentListAdapter(val apartmentList: Array<Apartment>): RecyclerView.Adapter<ApartmentListViewHolder>() {
    override fun getItemCount(): Int {
        return apartmentList.size
    }


    override fun onBindViewHolder(holder: ApartmentListViewHolder, position: Int) {
        if(MyApp.isLogged){
            handleLoggedUser(holder, position)
        }else{
         handleNotLoggedUser(holder, position)
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

    private fun handleNotLoggedUser(holder: ApartmentListViewHolder, position: Int){
        holder.view.apartment_name_text_view_not_logged.text = apartmentList[position].apartmentName
        holder.view.apartment_address_text_view_not_logged.text = apartmentList[position].apartmentCity + ", " +
                apartmentList[position].apartmentStreet
        holder.view.apartment_price_text_view_not_logged.text = apartmentList[position].apartmentPrice.toString() + " zł"

        if(apartmentList[position].apartmentImage != null){
            val string = apartmentList[position].apartmentImage.substring(21)
            val imageBytes = Base64.decode(string, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            if(decodedImage != null){
                holder.view.image_main_apartment_not_logged.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        decodedImage, 130, 130, false
                    )
                )
            }else{
                holder.view.image_main_apartment_not_logged.setImageBitmap(decodedImage)
            }

        }else{
            holder.view.image_main_apartment_not_logged.setImageResource(R.drawable.icon)
        }
    }

    private fun handleLoggedUser(holder: ApartmentListViewHolder, position: Int) {
        holder.view.apartment_name_text_view_logged.text = apartmentList[position].apartmentName
        holder.view.apartment_address_text_view_logged.text = apartmentList[position].apartmentCity + ", " +
                apartmentList[position].apartmentStreet + " " + apartmentList[position].apartmentStreetNumber
        holder.view.apartment_zip_code_text_view_logged.text = apartmentList[position].apartmentZipCode
        holder.view.apartment_price_text_view_logged.text = apartmentList[position].apartmentPrice.toString() + " zł"
        holder.view.description_text_view_logged.text = apartmentList[position].apartmentDescription

        if(apartmentList[position].apartmentImage != null){
            val string = apartmentList[position].apartmentImage.substring(21)
            val imageBytes = Base64.decode(string, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            if(decodedImage != null){
                holder.view.image_main_apartment_logged.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        decodedImage, 130, 130, false
                    )
                )
            }else{
                holder.view.image_main_apartment_logged.setImageBitmap(decodedImage)
            }

        }else{
            holder.view.image_main_apartment_logged.setImageResource(R.drawable.icon)
        }

        val mainCardView = holder.view.apartment_not_logged_cell_card_view_logged

        mainCardView.setOnClickListener{
            val apartmentDetailsFragment = ApartmentDetailsFragment.newInstance()
            val manager = (holder.itemView.context as MainActivity).supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.frame_layout, apartmentDetailsFragment)
                ?.addToBackStack(ApartmentDetailsFragment.toString())
                ?.commit()
        }
    }
}