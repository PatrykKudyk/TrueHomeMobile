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
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_registration.view.*
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

    private lateinit var animationHolder : AnimationsHolder
    private val validationHolder = ValidationHolder()
    private lateinit var errorsHandler : ErrorsHandler
    private val jsonHolder = JsonHolder()


    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_registration, container, false);
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
        errorsHandler = ErrorsHandler(rootView.context)
        animationHolder = AnimationsHolder(rootView.context)
//        makeStartAnimations()
        initFonts()
        rootView.register_button.setOnClickListener {
            handleRegisterButton()
        }
        rootView.backTextView.setOnClickListener{
            fragmentManager
                ?.popBackStackImmediate()
        }
        handleFieldsListeners()
    }

    fun handleRegisterButton(){
        if(areFieldsCorrect()){
            //if(checkApiResponse()){
//                makeSuccessActions()
                Toast.makeText(rootView.context, "Pomyślnie zarejestrowano", Toast.LENGTH_SHORT).show()
                fragmentManager
                    ?.popBackStackImmediate()

            //}
        }
    }

    fun handleFieldsListeners(){
        handleLoginField()
        handleEmailField()
        handleEmailRepeatField()
        handlePasswordField()
        handlePasswordRepeatField()
    }

    private fun handlePasswordRepeatField() {
        rootView.password_repeat_field.addTextChangedListener(object : TextWatcher {
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
        rootView.password_field.addTextChangedListener(object : TextWatcher {
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
        rootView.email_repeat_field.addTextChangedListener(object : TextWatcher {
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
        rootView.email_field.addTextChangedListener(object : TextWatcher {
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
        rootView.login_field.addTextChangedListener(object : TextWatcher {
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
        errorsHandler.clearError(rootView.email_field_layout)
        if(validationHolder.isEmailCorrect(rootView.email_field.text.toString())){
            return true
        }
        errorsHandler.setIncorrectEmailError(rootView.email_field_layout)
        return false
    }

    private fun isPasswordCorrect(): Boolean {
        if(validationHolder.isPasswordCorrect(rootView.password_field.text.toString())){
            errorsHandler.clearError(rootView.password_field_layout)
            return true
        }
        errorsHandler.setIncorrectPasswordError(rootView.password_field_layout)
        return false
    }

    private fun isPasswordLengthCorrect(): Boolean{
        if(validationHolder.isLengthCorrect(rootView.password_field.text.toString(), 8)){
            errorsHandler.clearError(rootView.password_field_layout)
            return true
        }
        errorsHandler.setPasswordLengthError(password_field_layout)
        return false
    }

    private fun isLoginLengthCorrect(): Boolean {
        if(validationHolder.isLengthCorrect(rootView.login_field.text.toString(), 4)){
            errorsHandler.clearError(rootView.login_field_layout)
            return true
        }
        errorsHandler.setLoginLengthError(rootView.login_field_layout)
        return false
    }

    private fun areEmailsEqual(): Boolean {
        if(validationHolder.areFieldsEqual(rootView.email_field, rootView.email_repeat_field)){
            errorsHandler.clearError(rootView.email_repeat_field_layout)
            return true
        }
        errorsHandler.setEmailsNotEqualError(rootView.email_repeat_field_layout)
        return false
    }

    private fun arePasswordsEqual(): Boolean {
        if(validationHolder.areFieldsEqual(rootView.password_field, rootView.password_repeat_field)){
            errorsHandler.clearError(rootView.password_repeat_field_layout)
            return true
        }
        errorsHandler.setPasswordsNotEqualError(rootView.password_repeat_field_layout)
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
        if(!validationHolder.isFieldFilled(rootView.login_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(rootView.login_field_layout)
        }
        if(!validationHolder.isFieldFilled(rootView.email_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(rootView.email_field_layout)
        }
        if(!validationHolder.isFieldFilled(rootView.email_repeat_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(rootView.email_repeat_field_layout)
        }
        if(!validationHolder.isFieldFilled(rootView.password_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(rootView.password_field_layout)
        }
        if(!validationHolder.isFieldFilled(rootView.password_repeat_field)){
            isCorrect = false
            errorsHandler.setEmptyFieldError(rootView.password_repeat_field_layout)
        }
        return isCorrect
    }

    private fun clearFieldErrors(){
        errorsHandler.clearError(rootView.login_field_layout)
        rootView.login_field.clearFocus()
        errorsHandler.clearError(rootView.email_field_layout)
        rootView.email_field.clearFocus()
        errorsHandler.clearError(rootView.email_repeat_field_layout)
        rootView.email_repeat_field.clearFocus()
        errorsHandler.clearError(rootView.password_field_layout)
        rootView.password_field.clearFocus()
        errorsHandler.clearError(rootView.password_repeat_field_layout)
        rootView.password_repeat_field.clearFocus()
    }

    private fun makeSuccessActions() {
        animationHolder.flyaway(rootView.registration_card_view, 500, 0, 3)
        rootView.registration_card_view.visibility = View.GONE
        rootView.registeredTextView.visibility = View.VISIBLE
    }

//    private fun makeStartAnimations(){
//        animationHolder.popUp(registration_card_view, 700, 1000)
//        animationHolder.flyFromBottom(logoImageView, 700, 900)
//    }

    private fun initFonts(){
        val typeface = ResourcesCompat.getFont(rootView.context, R.font.josefinsansregular)
        rootView.password_field_layout.typeface = typeface
        rootView.password_repeat_field_layout.typeface = typeface
    }

    private fun checkApiResponse(): Boolean {
        val registrationRequest = RegistrationRequest(
            rootView.login_field.text.toString(),
            rootView.password_field.text.toString(),
            rootView.email_field.text.toString()
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
