package com.e.truehomemobile.adapters.page

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.e.truehomemobile.R
import com.e.truehomemobile.activities.MainActivity
import com.e.truehomemobile.fragments.Apartment.ImageFragment
import kotlinx.android.synthetic.main.apartment_image.view.*

class ImagesViewPagerAdapter : PagerAdapter {

    var context: Context
    var images: Array<String>
    lateinit var inflater: LayoutInflater

    constructor(context: Context, images: Array<String>) : super() {
        this.context = context
        this.images = images
    }


    override fun getCount(): Int = images.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as LinearLayout

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var image: ImageView
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.apartment_image, container, false)
        image = view.apartment_details_image

        val string = images[position].substring(21)
        val imageBytes = Base64.decode(string, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        image.setImageBitmap(
            Bitmap.createBitmap(decodedImage)
        )
        container!!.addView(view)

        view.setOnClickListener {
            val imageFragment = ImageFragment.newInstance(images[position])
            val manager = (context as MainActivity).supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.frame_layout, imageFragment)
                ?.addToBackStack(ImageFragment.toString())
                ?.commit()
        }

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as LinearLayout)
    }
}