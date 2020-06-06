package com.e.truehomemobile.adapters.recycler

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.e.truehomemobile.MyApp
import com.e.truehomemobile.R
import com.e.truehomemobile.activities.MainActivity
import com.e.truehomemobile.adapters.page.ImagesViewPagerAdapter
import com.e.truehomemobile.fragments.apartment.MyApartmentDetailsFragment
import com.e.truehomemobile.models.apartment.ApartmentWithImages
import com.e.truehomemobile.viewHolders.ApartmentDetailsViewHolder
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.apartment_my_details.view.*
import kotlinx.android.synthetic.main.apartment_show_details.view.*
import kotlinx.android.synthetic.main.apartment_show_details.view.apartment_details_address
import kotlinx.android.synthetic.main.apartment_show_details.view.apartment_details_city
import kotlinx.android.synthetic.main.apartment_show_details.view.apartment_details_description
import kotlinx.android.synthetic.main.apartment_show_details.view.apartment_details_name
import kotlinx.android.synthetic.main.apartment_show_details.view.apartment_details_price
import kotlinx.android.synthetic.main.apartment_show_details.view.apartment_details_view_pager
import kotlinx.android.synthetic.main.fragment_my_apartment_details.view.*
import okhttp3.*
import java.io.IOException
import java.security.cert.CertificateException
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MyApartmentDetailsAdapter(val apartment: ApartmentWithImages) :
    RecyclerView.Adapter<ApartmentDetailsViewHolder>() {
    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.apartment_my_details, parent, false)
        return ApartmentDetailsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ApartmentDetailsViewHolder, position: Int) {
        holder.view.apartment_details_name.text = apartment.apartmentPartialResult.apartmentName
        holder.view.apartment_details_city.text =
            apartment.apartmentPartialResult.apartmentCity + ", " +
                    apartment.apartmentPartialResult.apartmentZipCode
        holder.view.apartment_details_address.text =
            apartment.apartmentPartialResult.apartmentStreet + " " + apartment.apartmentPartialResult.apartmentStreetNumber
        holder.view.apartment_details_price.text =
            apartment.apartmentPartialResult.apartmentPrice.toString() + " zł"
        holder.view.apartment_details_description.text =
            apartment.apartmentPartialResult.apartmentDescription
//        if(apartment.apartmentPartialResult.user.telephoneNumber != null){
//            holder.view.apartment_details_phone.text = apartment.apartmentPartialResult.user.telephoneNumber
//        }

        var adapter: PagerAdapter = ImagesViewPagerAdapter(
            holder.view.context,
            apartment.apartmentPartialResult.apartmentImages
        )
        holder.view.apartment_details_view_pager.adapter = adapter

        holder.view.delete_apartment_button.setOnClickListener {
            deleteApartment(holder)
        }

    }

    private fun deleteApartment(holder: ApartmentDetailsViewHolder) {
        val url = MyApp.homeUrl +
                "Apartments/DeleteApartment/" + apartment.apartmentPartialResult.apartmentId

        val request = Request.Builder()
            .url(url)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + MyApp.token)
            .delete()
            .build()

        val client: OkHttpClient = getUnsafeOkHttpClient().build()

        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                (holder.view.context as Activity).runOnUiThread {
                    Toast.makeText(
                        holder.view.context,
                        holder.view.context.getString(
                            getStringIdentifier(
                                holder.view.context,
                                "toast_not_deleted"
                            )
                        ),
                        Toast.LENGTH_SHORT
                    )
                }
            }

            override fun onResponse(call: Call, response: Response) {
                when (response.code) {

                    200 -> {
                        (holder.view.context as Activity).runOnUiThread {
                            Toast.makeText(
                                holder.view.context,
                                holder.view.context.getString(
                                    getStringIdentifier(
                                        holder.view.context,
                                        "toast_successfully_deleted"
                                    )
                                ),
                                Toast.LENGTH_SHORT
                            )
                        }
                        val manager =
                            (holder.itemView.context as MainActivity).supportFragmentManager
                        manager
                            ?.popBackStack()
                    }


                    else -> {

                    }
                }
            }

        })
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {

        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })

            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    private fun getStringIdentifier(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "string", context.packageName)
    }
}