package com.e.truehomemobile.activityHolders.logic

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.res.ResourcesCompat
import com.e.truehomemobile.MyApp
import com.e.truehomemobile.activities.RegistrationActivity
import com.e.truehomemobile.activityHolders.AnimationsHolder
import com.e.truehomemobile.activityHolders.ErrorsHandler
import com.e.truehomemobile.activityHolders.ValidationHolder
import com.e.truehomemobile.R
import com.e.truehomemobile.models.authorization.LoginRequest
import com.e.truehomemobile.models.authorization.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.security.cert.CertificateException
import java.sql.Types.NULL
import javax.net.ssl.*

class LoginLogicHolder(private val context: Context, private val activity: Activity) {

    private val animationHolder = AnimationsHolder(context)
    private val validationHolder = ValidationHolder()
    private val errorsHolder = ErrorsHandler(context)
    private val jsonHolder = JsonHolder()

    fun initActivity(){
        makeStartAnimations()
        initFonts()
    }

    fun handleRegisterButton(){
        val intent = Intent(context, RegistrationActivity::class.java)
        activity.startActivityForResult(intent, 0)
        clearFields()
    }

    fun handleLoginButton(){
        clearFieldsErrors()
        if(areFieldsFilled()){
            if(checkUserDataCorrectness()){
                activity.login_field.text = null
                activity.password_field.text = null
            }
        }
    }

    private fun areFieldsFilled(): Boolean {
        var isCorrect = false
        if(!validationHolder.isFieldFilled(activity.login_field)){
            errorsHolder.setEmptyFieldError(activity.login_field_layout)
        }else{
            isCorrect = true
        }
        if(!validationHolder.isFieldFilled(activity.password_field)) {
            errorsHolder.setEmptyFieldError(activity.password_field_layout)
            isCorrect = false
        }
        return isCorrect
    }

    private fun clearFields(){
        clearFieldsErrors()
        activity.login_field.text = null
        activity.password_field.text = null
    }

    private fun clearFieldsErrors(){
        errorsHolder.clearError(activity.login_field_layout)
        errorsHolder.clearError(activity.password_field_layout)
        activity.login_field.clearFocus()
        activity.password_field.clearFocus()
    }

    private fun makeStartAnimations(){
        animationHolder.popUp(activity.login_card_view, 1000, 250)
        animationHolder.fallFromTop(activity.logoImageView,750, 300)
        animationHolder.popUp(activity.login_field_layout, 600, 1000)
        animationHolder.popUp(activity.password_field_layout, 600, 1000)
        animationHolder.flyFromBottom(activity.login_button, 400, 1500)
        animationHolder.flyFromBottom(activity.register_button, 400, 1500)
    }

    private fun initFonts(){
        val typeface = ResourcesCompat.getFont(context, R.font.josefinsansregular)
        activity.password_field_layout.typeface = typeface
    }

    private fun checkUserDataCorrectness(): Boolean{
        val loginRequest = LoginRequest(
            activity.login_field.text.toString(),
            activity.password_field.text.toString()
        )

        MyApp.isRequestReceived = false
        fetchApiLoginResponse(loginRequest)

        do{
        Thread.sleep(100)
        }while(!MyApp.isRequestReceived)

        if(MyApp.loginResponse.token != ""){
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

        val response = client.newCall(request).enqueue(object: Callback{
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
                        MyApp.loginResponse = LoginResponse(NULL,"","","",
                            NULL,ArrayList())
                    }
                }
                MyApp.isRequestReceived = true
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