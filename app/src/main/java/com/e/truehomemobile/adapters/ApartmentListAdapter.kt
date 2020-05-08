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
import com.e.truehomemobile.models.apartment.Apartment
import com.e.truehomemobile.viewHolders.ApartmentListViewHolder
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
        holder.view.apartment_name_text_view.text = apartmentList[position].apartmentName
        holder.view.apartment_address_text_view.text = apartmentList[position].apartmentCity + ", " +
                apartmentList[position].apartmentStreet + " " + apartmentList[position].apartmentStreetNumber
        holder.view.apartment_zip_code_text_view.text = apartmentList[position].apartmentZipCode
        holder.view.apartment_price_text_view.text = apartmentList[position].apartmentPrice.toString() + " zł"
        holder.view.description_text_view.text = apartmentList[position].apartmentDescription

        if(apartmentList[position].apartmentImage != null){
            val string = apartmentList[position].apartmentImage.substring(21)
            val imageBytes = Base64.decode(string, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            if(decodedImage != null){
                holder.view.image_main_apartment.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        decodedImage, 130, 130, false
                    )
                )
            }else{
                holder.view.image_main_apartment.setImageBitmap(decodedImage)
            }

        }else{
            holder.view.image_main_apartment.setImageResource(R.drawable.icon)
        }

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

    private fun handleLoggedUser(holder: ApartmentListViewHolder, position: Int) {

    }
}