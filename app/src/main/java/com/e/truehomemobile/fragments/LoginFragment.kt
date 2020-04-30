package com.e.truehomemobile.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.MyApp

import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.AnimationsHolder
import com.e.truehomemobile.activityHolders.ErrorsHandler
import com.e.truehomemobile.activityHolders.JsonHolder
import com.e.truehomemobile.activityHolders.ValidationHolder
import com.e.truehomemobile.models.authorization.LoginRequest
import com.e.truehomemobile.models.authorization.LoginResponse
import com.google.android.material.navigation.NavigationView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main_layout.*
import kotlinx.android.synthetic.main.activity_main_layout.view.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
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
    private lateinit var errorsHandler : ErrorsHandler
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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


    private fun initFragment(){
        errorsHandler = ErrorsHandler(rootView.context)
//        animationHolder = AnimationsHolder(frame_layout.context)
//        makeStartAnimations()

//        loginField = rootView.findViewById(R.id.login_field)
//        loginFieldLayout = rootView.findViewById(R.id.login_field_layout)
//        passwordField = rootView.findViewById(R.id.password_field)
//        passwordFieldLayout = rootView.findViewById(R.id.password_field_layout)
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

    private fun handleRegisterButton(){
        registrationFragment = RegistrationFragment.newInstance()
        clearFields()
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.frame_layout, registrationFragment)
            ?.addToBackStack(RegistrationFragment.toString())
            ?.commit()
    }

    private fun handleLoginButton(){
//        MyApp.isLogged = false                   // USUNĄĆ TO JAK JUŻ BĘDZIE LOGOWANIE
//        clearFieldsErrors()
//        if(areFieldsFilled()){
//            if(checkUserDataCorrectness()){
//                login_field.text = null
//                password_field.text = null
//            }
//        }
        if(rootView.login_field.text.toString() == "admin" && rootView.password_field.text.toString() == "admin"){
            Toast.makeText(rootView.context,"Pomyślnie zalogowano", Toast.LENGTH_SHORT).show()
            clearFields()
            MyApp.isLogged = true

            apartmentListFragment = ApartmentListFragment.newInstance()
            navigationView = activity?.findViewById(R.id.nav_view) as NavigationView

            val menu = navigationView.menu
            menu.findItem(R.id.menu_account).setTitle(R.string.menu_account)
            navigationView.setCheckedItem(menu.findItem(R.id.menu_apartments))

            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.frame_layout, apartmentListFragment)
                ?.commit()

        }else{
            clearFields()
            Toast.makeText(rootView.context,"Błędne dane logowania", Toast.LENGTH_SHORT).show()
        }
    }

    private fun areFieldsFilled(): Boolean {
        var isCorrect = false
        if(!validationHolder.isFieldFilled(login_field)){
            errorsHandler.setEmptyFieldError(login_field_layout)
        }else{
            isCorrect = true
        }
        if(!validationHolder.isFieldFilled(password_field)) {
            errorsHandler.setEmptyFieldError(password_field_layout)
            isCorrect = false
        }
        return isCorrect
    }

    private fun clearFields(){
        clearFieldsErrors()
        rootView.login_field.text = null
        rootView.password_field.text = null
    }

    private fun clearFieldsErrors(){
        errorsHandler.clearError(rootView.login_field_layout)
        errorsHandler.clearError(rootView.password_field_layout)
        rootView.login_field.clearFocus()
        rootView.password_field.clearFocus()
    }

//    private fun makeStartAnimations(){
//        animationHolder.popUp(login_card_view, 1000, 250)
//        animationHolder.fallFromTop(logoImageView,750, 300)
//        animationHolder.popUp(login_field_layout, 600, 1000)
//        animationHolder.popUp(password_field_layout, 600, 1000)
//        animationHolder.flyFromBottom(login_button, 400, 1500)
//        animationHolder.flyFromBottom(register_button, 400, 1500)
//    }

    private fun initFonts(){
        val typeface = ResourcesCompat.getFont(rootView.context, R.font.josefinsansregular)
        rootView.password_field_layout.typeface = typeface
    }

    private fun checkUserDataCorrectness(): Boolean{
        val loginRequest = LoginRequest(
            login_field.text.toString(),
            password_field.text.toString()
        )

        MyApp.isResponseReceived = false
        fetchApiLoginResponse(loginRequest)

        do{
            Thread.sleep(100)
        }while(!MyApp.isResponseReceived)

        if(MyApp.loginResponse.token != ""){
            MyApp.isLogged = true
            return true
        }
        return false
    }

    private fun fetchApiLoginResponse(loginRequest: LoginRequest){
        val url = MyApp.apiUrl + "security/login"
        val json = jsonHolder.createLoginRequestJson(loginRequest).trimIndent()
        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val client: OkHttpClient = getUnsafeOkHttpClient().build()

        val response = client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call, response: Response) {
                when (response.code) {

                    200 -> {
                        val body = response.body?.string()

                        val gson = GsonBuilder().create()

                        MyApp.loginResponse = gson.fromJson(body, LoginResponse::class.java)
                    }

                    else -> {
                        MyApp.loginResponse = LoginResponse(
                            Types.NULL,"","","",
                            Types.NULL,ArrayList())
                    }
                }
                MyApp.isResponseReceived = true
            }
        })
    }


    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder{

        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
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
