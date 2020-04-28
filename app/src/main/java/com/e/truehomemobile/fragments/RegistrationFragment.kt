package com.e.truehomemobile.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
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
import com.e.truehomemobile.models.authorization.RegistrationRequest
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_registration.*
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
 * Activities that contain this fragment must implement the
 * [RegistrationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var animationHolder : AnimationsHolder
    private val validationHolder = ValidationHolder()
    lateinit var errorsHandler: ErrorsHandler
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
        return inflater.inflate(R.layout.fragment_registration, container, false)
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
         * @return A new instance of fragment RegistrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
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
//        if(areFieldsCorrect()){
//            if(checkApiResponse()){
//                makeSuccessActions()
//                Handler().postDelayed({
//                    val intent = Intent()
//                    intent.putExtra("login", login_field.text.toString())
//                    setResult(Activity.RESULT_OK, intent)
//                    finish()
//                }, 2000)
//            }
//        }
//    }

    fun handleFieldsListeners(){
        handleLoginField()
        handleEmailField()
        handleEmailRepeatField()
        handlePasswordField()
        handlePasswordRepeatField()
    }

    private fun handlePasswordRepeatField() {
        password_repeat_field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                arePasswordsEqual()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun handlePasswordField() {
        password_field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(isPasswordLengthCorrect()){
                    isPasswordCorrect()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun handleEmailRepeatField() {
        email_repeat_field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                areEmailsEqual()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun handleEmailField() {
        email_field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isEmailCorrect()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun handleLoginField(){
        login_field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isLoginLengthCorrect()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun isEmailCorrect(): Boolean {
        errorsHandler.clearError(email_field_layout)
        if(validationHolder.isEmailCorrect(email_field.text.toString())){
            return true
        }
        errorsHandler.setIncorrectEmailError(email_field_layout)
        return false
    }

    private fun isPasswordCorrect(): Boolean {
        if(validationHolder.isPasswordCorrect(password_field.text.toString())){
            errorsHandler.clearError(password_field_layout)
            return true
        }
        errorsHandler.setIncorrectPasswordError(password_field_layout)
        return false
    }

    private fun isPasswordLengthCorrect(): Boolean{
        if(validationHolder.isLengthCorrect(password_field.text.toString(), 8)){
            errorsHandler.clearError(password_field_layout)
            return true
        }
        errorsHandler.setPasswordLengthError(password_field_layout)
        return false
    }

    private fun isLoginLengthCorrect(): Boolean {
        if(validationHolder.isLengthCorrect(login_field.text.toString(), 4)){
            errorsHandler.clearError(login_field_layout)
            return true
        }
        errorsHandler.setLoginLengthError(login_field_layout)
        return false
    }

    private fun areEmailsEqual(): Boolean {
        if(validationHolder.areFieldsEqual(email_field, email_repeat_field)){
            errorsHandler.clearError(email_repeat_field_layout)
            return true
        }
        errorsHandler.setEmailsNotEqualError(email_repeat_field_layout)
        return false
    }

    private fun arePasswordsEqual(): Boolean {
        if(validationHolder.areFieldsEqual(password_field, password_repeat_field)){
            errorsHandler.clearError(password_repeat_field_layout)
            return true
        }
        errorsHandler.setPasswordsNotEqualError(password_repeat_field_layout)
        return false
    }

    private fun makeFirstLayerFieldTests(): Boolean{
        var isPassed = arePasswordsEqual()
        if(!areEmailsEqual()){
            isPassed = false
        }
        if(!isLoginLengthCorrect()){
            isPassed = false
        }
        return isPassed
    }

    private fun makeSecondLayerFieldTests(): Boolean{
        var isPassed = isPasswordCorrect()
        if(!isEmailCorrect()) {
            isPassed = false
        }
        return isPassed
    }

    private fun areFieldsCorrect(): Boolean {
        if(areAllFieldsFilled()){
            if(makeFirstLayerFieldTests()){  //i had to create a method there otherwise, the test were not ran simultaneously
                if(makeSecondLayerFieldTests()){                        //same as previously
                    return true
                }
                return false
            }
            return false
        }
        return false
    }

    private fun areAllFieldsFilled(): Boolean {
        clearFieldErrors()
        var isCorrect = true
        if(!validationHolder.isFieldFilled(login_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(login_field_layout)
        }
        if(!validationHolder.isFieldFilled(email_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(email_field_layout)
        }
        if(!validationHolder.isFieldFilled(email_repeat_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(email_repeat_field_layout)
        }
        if(!validationHolder.isFieldFilled(password_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(password_field_layout)
        }
        if(!validationHolder.isFieldFilled(password_repeat_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(password_repeat_field_layout)
        }
        return isCorrect
    }

    private fun clearFieldErrors(){
        errorsHandler.clearError(login_field_layout)
        login_field.clearFocus()
        errorsHandler.clearError(email_field_layout)
        email_field.clearFocus()
        errorsHandler.clearError(email_repeat_field_layout)
        email_repeat_field.clearFocus()
        errorsHandler.clearError(password_field_layout)
        password_field.clearFocus()
        errorsHandler.clearError(password_repeat_field_layout)
         password_repeat_field.clearFocus()
    }

    private fun makeSuccessActions() {
        animationHolder.flyaway(registration_card_view, 500, 0, 3)
        registration_card_view.visibility = View.GONE
        registeredTextView.visibility = View.VISIBLE
    }

    private fun makeStartAnimations(){
        animationHolder.popUp(registration_card_view, 700, 1000)
        animationHolder.flyFromBottom(logoImageView, 700, 900)
    }

    private fun initFonts(){
        val typeface = ResourcesCompat.getFont(frame_layout.context, R.font.josefinsansregular)
        password_field_layout.typeface = typeface
        password_repeat_field_layout.typeface = typeface
    }

    private fun checkApiResponse(): Boolean {
        val registrationRequest = RegistrationRequest(
            login_field.text.toString(),
            password_field.text.toString(),
            email_field.text.toString()
        )
        MyApp.isResponseReceived = false
        fetchApiResponse(registrationRequest)
        do{
            Thread.sleep(100)
        }while(!MyApp.isResponseReceived)

        return true
    }

    private fun fetchApiResponse(registrationRequest: RegistrationRequest){
        val url = MyApp.apiUrl + "security/registration"
        val json = jsonHolder.createRegistrationRequestJson(registrationRequest).trimIndent()
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



                    }

                    else -> {



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
