package com.e.truehomemobile.fragments.account

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.MyApp

import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.ErrorsHandler
import com.e.truehomemobile.activityHolders.JsonHolder
import com.e.truehomemobile.activityHolders.ValidationHolder
import com.e.truehomemobile.fragments.apartment.ApartmentListFragment
import com.e.truehomemobile.models.authorization.LoginRequest
import com.e.truehomemobile.models.authorization.LoginResponse
import com.google.android.material.navigation.NavigationView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main_layout.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.nav_header.view.user_name_text_view
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.security.cert.CertificateException
import java.sql.Types
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var registrationFragment: RegistrationFragment
    private lateinit var apartmentListFragment: ApartmentListFragment


    //    private lateinit var animationHolder : AnimationsHolder
    private val validationHolder = ValidationHolder()
    private lateinit var errorsHandler: ErrorsHandler
    private val jsonHolder = JsonHolder()


    private lateinit var rootView: View

    //    private lateinit var loginField: View
//    private lateinit var loginFieldLayout: View
//    private lateinit var passwordField: View
//    private lateinit var passwordFieldLayout: View
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var navigationView: NavigationView

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
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
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
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    //                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun initFragment() {
        errorsHandler = ErrorsHandler(rootView.context)

        loginButton = rootView.findViewById(R.id.login_button)
        registerButton = rootView.findViewById(R.id.register_button)

        initFonts()

        registerButton.setOnClickListener {
            handleRegisterButton()
        }

        loginButton.setOnClickListener {
            handleLoginButton()
        }
    }

    private fun handleRegisterButton() {
        registrationFragment =
            RegistrationFragment.newInstance()
        clearFields()
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.frame_layout, registrationFragment)
            ?.addToBackStack(RegistrationFragment.toString())
            ?.commit()
    }

    private fun handleLoginButton() {
        if (areFieldsFilled()) {
            //     if (checkUserDataCorrectness()) {
            if (rootView.login_field.text.toString() == "papryk" && rootView.password_field.text.toString() == "Papryk12") {
                Toast.makeText(
                    rootView.context,
                    getString(getStringIdentifier(rootView.context, "toast_successfully_logged")),
                    Toast.LENGTH_SHORT
                ).show()
                MyApp.isLogged = true
                MyApp.userLogin = login_field.text.toString()
                MyApp.token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2IiwidW5pcXVlX25hbWUiOiI2IiwianRpIjoiMTI3ZjRhZTctYzc4My00MGM4LTk3ZDktZjMzN2JkN2QwZjAzIiwiaWF0IjoxNTkxNDU2MDY5MzIwLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJ1c2VyIiwibmJmIjoxNTkxNDU2MDY5LCJleHAiOjE1OTE0NTc4NjksImlzcyI6Imh0dHA6Ly81NC4zOC41NS44MjoxODc2NSIsImF1ZCI6Imh0dHBzOi8veW91cl9hcHA6NDQzIn0.47nEp1Dlso5C19ettgpWVhcZUa8JKwjopqjgccj5fss"
                clearFields()
                apartmentListFragment = ApartmentListFragment.newInstance()
                navigationView = activity?.findViewById(R.id.nav_view) as NavigationView

                val menu = navigationView.menu
                menu.findItem(R.id.menu_account).setTitle(R.string.menu_account)
                navigationView.setCheckedItem(menu.findItem(R.id.menu_apartments))

                navigationView.user_name_text_view.text = MyApp.userLogin

                fragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.frame_layout, apartmentListFragment)
                    ?.commit()

            } else {
                clearFields()
            }
        }

    }

    private fun areFieldsFilled(): Boolean {
        var isCorrect = false
        if (!validationHolder.isFieldFilled(login_field)) {
            errorsHandler.setEmptyFieldError(login_field_layout)
        } else {
            isCorrect = true
        }
        if (!validationHolder.isFieldFilled(password_field)) {
            errorsHandler.setEmptyFieldError(password_field_layout)
            isCorrect = false
        }
        return isCorrect
    }

    private fun clearFields() {
        clearFieldsErrors()
        rootView.login_field.text = null
        rootView.password_field.text = null
    }

    private fun clearFieldsErrors() {
        errorsHandler.clearError(rootView.login_field_layout)
        errorsHandler.clearError(rootView.password_field_layout)
        rootView.login_field.clearFocus()
        rootView.password_field.clearFocus()
    }

    private fun initFonts() {
        val typeface = ResourcesCompat.getFont(rootView.context, R.font.josefinsansregular)
        rootView.password_field_layout.typeface = typeface
    }

    private fun checkUserDataCorrectness(): Boolean {
        val loginRequest = LoginRequest(
            login_field.text.toString(),
            password_field.text.toString()
        )

        MyApp.isResponseReceived = false
        fetchApiLoginResponse(loginRequest)

        do {
            Thread.sleep(100)
        } while (!MyApp.isResponseReceived)

        if (MyApp.token != "") {
            MyApp.isLogged = true
            return true
        }
        return false
    }

    private fun fetchApiLoginResponse(loginRequest: LoginRequest) {
        val url = MyApp.identityUrl + "Auth/Login"
        val json = jsonHolder.createLoginRequestJson(loginRequest).trimIndent()
        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
//            .addHeader("Accept-Charset", "charset=utf-8")
//            .addHeader("Accept-Language", "*/*")
//            .header("Content-Type", "application/json")
            .post(requestBody)
            .build()

//        Log.println(Log.INFO,"INFO", request.toString())

        val client: OkHttpClient = getUnsafeOkHttpClient().build()

        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                MyApp.isResponseReceived = true
                activity?.runOnUiThread {
                    Toast.makeText(
                        rootView.context,
                        getString(
                            getStringIdentifier(
                                rootView.context,
                                "toast_unable_to_get_respond"
                            )
                        )
                        , Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                when (response.code) {

                    200 -> {
                        val body = response.body?.string()

                        val gson = GsonBuilder().create()

                        val loginResponse = gson.fromJson(body, LoginResponse::class.java)
                        MyApp.token = loginResponse.accessToken
                        MyApp.refreshToken = loginResponse.refreshToken
                    }

                    406 -> {
                        activity?.runOnUiThread {
                            Toast.makeText(
                                rootView.context,
                                getString(
                                    getStringIdentifier(
                                        rootView.context,
                                        "toast_unable_to_log_in"
                                    )
                                )
                                , Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    else -> {
                        activity?.runOnUiThread {
                            Toast.makeText(
                                rootView.context,
                                getString(
                                    getStringIdentifier(
                                        rootView.context,
                                        "toast_unable_to_log_in"
                                    )
                                )
                                , Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                MyApp.isResponseReceived = true
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
