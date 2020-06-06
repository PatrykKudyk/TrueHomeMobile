package com.e.truehomemobile.fragments.account

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.e.truehomemobile.MyApp
import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.ErrorsHandler
import com.e.truehomemobile.activityHolders.JsonHolder
import com.e.truehomemobile.adapters.recycler.UserInfoShowAdapter
import com.e.truehomemobile.models.user.User
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_edit_user_info.view.*
import kotlinx.android.synthetic.main.fragment_show_user_info.view.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
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
 * Use the [ShowUserInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditUserInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var saveChangesButton: View
    private val jsonHolder = JsonHolder()
    private lateinit var errorsHandler: ErrorsHandler


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
        rootView = inflater.inflate(R.layout.fragment_edit_user_info, container, false);
        initFragment()
        return rootView
    }

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

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            EditUserInfoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initFragment() {
        errorsHandler = ErrorsHandler(rootView.context)
        saveChangesButton = rootView.findViewById(R.id.save_changes_button)
        saveChangesButton.setOnClickListener {
            if (areFieldsFilled()) {
                putChanges()
            }
        }
    }

    private fun areFieldsFilled(): Boolean {
        var correctness = true
        if (rootView.user_edit_first_name.text.toString() == "") {
            errorsHandler.setEmptyFieldError(rootView.user_edit_first_name_layout)
            correctness = false
        }
        if (rootView.user_edit_last_name.text.toString() == "") {
            errorsHandler.setEmptyFieldError(rootView.user_edit_last_name_layout)
            correctness = false
        }
        if (rootView.user_edit_email.text.toString() == "") {
            errorsHandler.setEmptyFieldError(rootView.user_edit_email_field_layout)
            correctness = false
        }
        if (rootView.user_edit_username.text.toString() == "") {
            errorsHandler.setEmptyFieldError(rootView.user_edit_username_field_layout)
            correctness = false
        }
        if (rootView.user_edit_age.text.toString() == "") {
            errorsHandler.setEmptyFieldError(rootView.user_edit_age_field_layout)
            correctness = false
        }
        if (rootView.user_edit_street.text.toString() == "") {
            errorsHandler.setEmptyFieldError(rootView.user_edit_street_field_layout)
            correctness = false
        }
        if (rootView.user_edit_street_number.text.toString() == "") {
            errorsHandler.setEmptyFieldError(rootView.user_edit_street_number_field_layout)
            correctness = false
        }
        if (rootView.user_edit_phone_number.text.toString() == "") {
            errorsHandler.setEmptyFieldError(rootView.user_edit_phone_number_field_layout)
            correctness = false
        }
        return correctness
    }

    private fun putChanges() {
        val url = MyApp.homeUrl +
                "api/UserData"
        val user: User = User(
            1,
            rootView.user_edit_email.text.toString(),
            rootView.user_edit_username.text.toString(),
            rootView.user_edit_first_name.text.toString(),
            rootView.user_edit_last_name.text.toString(),
            rootView.user_edit_age.text.toString().toInt(),
            rootView.user_edit_street.text.toString(),
            rootView.user_edit_street_number.text.toString(),
            rootView.user_edit_phone_number.text.toString()
        )
        val json = jsonHolder.createUserUpdateJson(user).trimIndent()
        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + MyApp.token)
            .put(requestBody)
            .build()

        val client: OkHttpClient = getUnsafeOkHttpClient().build()

        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(
                        rootView.context,
                        getString(
                            getStringIdentifier(
                                rootView.context,
                                "toast_user_info_update_not_successful"
                            )
                        )
                        , Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                when (response.code) {

                    200 -> {
                        activity?.runOnUiThread {
                            Toast.makeText(
                                rootView.context,
                                getString(
                                    getStringIdentifier(
                                        rootView.context,
                                        "toast_user_info_update_successful"
                                    )
                                )
                                , Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    else -> {
                    }
                }
                fragmentManager
                    ?.popBackStack()
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


