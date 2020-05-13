package com.e.truehomemobile.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.truehomemobile.R
import com.e.truehomemobile.activities.MainActivity
import com.e.truehomemobile.fragments.Apartment.ImageFragment
import com.e.truehomemobile.viewHolders.ApartmentImagesViewHolder
import kotlinx.android.synthetic.main.apartment_image.view.*

class ApartmentImagesAdapter(val imagesList: Array<String>) :
    RecyclerView.Adapter<ApartmentImagesViewHolder>() {
    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentImagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.apartment_image, parent, false)
        return ApartmentImagesViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ApartmentImagesViewHolder, position: Int) {
        holder.view.apartment_details_image

        if (imagesList[position] != null) {
            val string = imagesList[position].substring(21)
            val imageBytes = Base64.decode(string, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            if (decodedImage != null) {
                holder.view.apartment_details_image.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        decodedImage, 150, 150, false
                    )
                )
            } else {
                holder.view.apartment_details_image.setImageBitmap(decodedImage)
            }
        } else {
            holder.view.apartment_details_image.setImageResource(R.drawable.icon)
        }

        holder.view.apartment_details_image.setOnClickListener {
            val imageFragment = ImageFragment.newInstance(imagesList[position])
            val manager = (holder.itemView.context as MainActivity).supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.frame_layout, imageFragment)
                ?.addToBackStack(ImageFragment.toString())
                ?.commit()
        }
    }
}