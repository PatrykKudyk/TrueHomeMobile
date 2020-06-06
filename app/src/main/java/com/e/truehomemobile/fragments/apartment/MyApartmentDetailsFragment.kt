package com.e.truehomemobile.fragments.apartment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.truehomemobile.MyApp

import com.e.truehomemobile.R
import com.e.truehomemobile.adapters.recycler.ApartmentDetailsAdapter
import com.e.truehomemobile.adapters.recycler.MyApartmentDetailsAdapter
import com.e.truehomemobile.models.apartment.ApartmentWithImages
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.apartment_my_details.view.*
import kotlinx.android.synthetic.main.fragment_my_apartment_details.view.*
import okhttp3.*
import java.io.IOException
import java.security.cert.CertificateException
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ImageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyApartmentDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Long? = null
    private var param2: Int? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
//    private lateinit var deleteButton: View
//    private lateinit var editButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getLong(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_my_apartment_details, container, false)
        initFragment()
        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Long) =
            MyApartmentDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                }
            }
    }

    private fun initFragment() {
        recyclerView = rootView.my_apartment_details_recycler_view
//        deleteButton = rootView.findViewById(R.id.delete_apartment_button)
//        editButton = rootView.findViewById(R.id.edit_apartment_button)

        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = mLayoutManager

//        deleteButton.setOnClickListener{
//            deleteApartment()
//        }
//
//        editButton.setOnClickListener {
//            editApartment()
//        }

        fetchApartment()
    }

    private fun editApartment(){

    }

    private fun deleteApartment(){

    }

    private fun fetchApartment() {
        rootView.no_data_error_text_view.visibility = View.GONE
        rootView.firstProgressBar.visibility = View.VISIBLE
        val url = MyApp.homeUrl +
                "Apartments/GetPartOfApartments/" + param1.toString()

        val request = Request.Builder()
            .url(url)
            .header("Content-Type", "application/json")
            .get()
            .build()

        val client: OkHttpClient = getUnsafeOkHttpClient().build()

        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    rootView.firstProgressBar.visibility = View.GONE
                    rootView.no_data_error_text_view.visibility = View.VISIBLE
                    rootView.no_data_error_text_view.setOnClickListener {
                        fetchApartment()
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                when (response.code) {

                    200 -> {
                        val body = response.body?.string()

                        val gson = GsonBuilder().create()

                        val apartmentFetched = gson.fromJson(body, ApartmentWithImages::class.java)

                        activity?.runOnUiThread {
                            rootView.no_data_error_text_view.visibility = View.GONE
                            rootView.firstProgressBar.visibility = View.GONE
                            recyclerView.adapter =
                                MyApartmentDetailsAdapter(
                                    apartmentFetched
                                )
                        }
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
}
