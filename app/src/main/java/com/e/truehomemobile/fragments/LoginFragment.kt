package com.e.truehomemobile.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.MyApp

import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.AnimationsHolder
import com.e.truehomemobile.activityHolders.ErrorsHandler
import com.e.truehomemobile.activityHolders.JsonHolder
import com.e.truehomemobile.activityHolders.ValidationHolder
import com.e.truehomemobile.models.authorization.LoginRequest
import com.e.truehomemobile.models.authorization.LoginResponse
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_login.*
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
    lateinit var animationHolder : AnimationsHolder
    private val validationHolder = ValidationHolder()
    lateinit var errorsHandler : ErrorsHandler
    private val jsonHolder = JsonHolder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
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
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun initFragment(){
        errorsHandler = ErrorsHandler(frame_layout.context)
        animationHolder = AnimationsHolder(frame_layout.context)
        makeStartAnimations()
        initFonts()
    }

//    fun handleRegisterButton(){
//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.frame_layout, apartmentListFragment)
//            .commit()
//        clearFields()
//    }

//    fun handleLoginButton(){
//        MyApp.isLogged = false                   // USUNĄĆ TO JAK JUŻ BĘDZIE LOGOWANIE
//        clearFieldsErrors()
//        if(areFieldsFilled()){
//            if(checkUserDataCorrectness()){
//                login_field.text = null
//                password_field.text = null
//            }
//        }
//    }

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
        login_field.text = null
        password_field.text = null
    }

    private fun clearFieldsErrors(){
        errorsHandler.clearError(login_field_layout)
        errorsHandler.clearError(password_field_layout)
        login_field.clearFocus()
        password_field.clearFocus()
    }

    private fun makeStartAnimations(){
        animationHolder.popUp(login_card_view, 1000, 250)
        animationHolder.fallFromTop(logoImageView,750, 300)
        animationHolder.popUp(login_field_layout, 600, 1000)
        animationHolder.popUp(password_field_layout, 600, 1000)
        animationHolder.flyFromBottom(login_button, 400, 1500)
        animationHolder.flyFromBottom(register_button, 400, 1500)
    }

    private fun initFonts(){
        val typeface = ResourcesCompat.getFont(frame_layout.context, R.font.josefinsansregular)
        password_field_layout.typeface = typeface
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
