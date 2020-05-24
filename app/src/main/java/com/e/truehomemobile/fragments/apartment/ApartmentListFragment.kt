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
import com.e.truehomemobile.activityHolders.JsonHolder
import com.e.truehomemobile.adapters.recycler.ApartmentListAdapter
import com.e.truehomemobile.models.apartment.Apartment
import com.e.truehomemobile.models.classes.MarginItemDecoration
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_apartment_list.*
import kotlinx.android.synthetic.main.fragment_apartment_list.view.*
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
 * [ApartmentListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ApartmentListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApartmentListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var jsonHolder: JsonHolder

    private lateinit var apartments: Array<Apartment>
    private var loading = false
    private var pageNumber: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_apartment_list, container, false);
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
     *
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
         * @return A new instance of fragment ApartmentListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ApartmentListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    fun initFragment() {
        recyclerView = rootView.findViewById(R.id.apartment_list_recycler_view)

        val mLayoutManager: LinearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = mLayoutManager

        recyclerView.addItemDecoration(
            MarginItemDecoration(
                12
            )
        )


        fetchApartments(pageNumber)

//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
////                if (dy > 0) { //check for scroll down
//                    val visibleItemCount = mLayoutManager.childCount
//                    val totalItemCount = mLayoutManager.itemCount
//                    val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
//
//                    if (!loading) {
//                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
//                            pageNumber++
//                            fetchApartments(pageNumber)
////                             Do pagination.. i.e. fetch new data
//                        }
//                    }
//                }
////            }
//        })

    }

    private fun fetchApartments(page: Int) {
        rootView.no_data_error_text_view.visibility = View.GONE
        rootView.firstProgressBar.visibility = View.VISIBLE

        loading = true
        if (page != 1) {
            rootView.secondProgressBar.visibility = View.VISIBLE
        }
        jsonHolder = JsonHolder()
        val url = MyApp.apiUrl +
                "Apartments/GetAllApartments" +
                "?PageNumber=1" +
                //   page.toString() +
                "&PageSize=6"

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
                    no_data_error_text_view.setOnClickListener {
                        fetchApartments(1)
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                when (response.code) {

                    200 -> {
                        val body = response.body?.string()

                        val gson = GsonBuilder().create()

                        val apartmentsFetched = gson.fromJson(body, Array<Apartment>::class.java)

//                        val apartmentList: ArrayList<Apartment> = apartmentsFetched

                        activity?.runOnUiThread {
                            rootView.no_data_error_text_view.visibility = View.GONE
                            if (page == 1) {
                                rootView.firstProgressBar.visibility = View.GONE
                                apartments = apartmentsFetched
                                recyclerView.adapter =
                                    ApartmentListAdapter(
                                        apartments
                                    )
                            } else {
                                apartments += apartmentsFetched
                                recyclerView.adapter?.notifyDataSetChanged()
                            }
                            loading = false
                            rootView.secondProgressBar.visibility = View.GONE
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
