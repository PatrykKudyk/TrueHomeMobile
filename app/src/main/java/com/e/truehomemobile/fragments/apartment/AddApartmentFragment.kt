package com.e.truehomemobile.fragments.apartment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.marginLeft
import com.e.truehomemobile.MyApp

import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.ErrorsHandler
import com.e.truehomemobile.activityHolders.ValidationHolder
import com.e.truehomemobile.models.apartment.Picture
import com.e.truehomemobile.models.classes.ImageFilePath
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_add_apartment.*
import kotlinx.android.synthetic.main.fragment_add_apartment.view.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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


class AddApartmentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var rootView: View
    private lateinit var addButton: Button
    private lateinit var backButton: View
    private lateinit var addImageButton: Button

    private var imagesArray = ArrayList<Picture>()

    private lateinit var errorsHandler: ErrorsHandler
    private val validationHolder = ValidationHolder()

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
        rootView = inflater.inflate(R.layout.fragment_add_apartment, container, false);
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

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddApartmentFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initFragment() {
        addButton = rootView.findViewById(R.id.add_apartment_button)
        backButton = rootView.findViewById(R.id.back_text_view)
        addImageButton = rootView.findViewById(R.id.add_images_button)
        errorsHandler = ErrorsHandler(rootView.context)

        addImageButton.setOnClickListener {
            startGallery()
        }

        addButton.setOnClickListener {
            addApartment()
        }

        backButton.setOnClickListener {
            fragmentManager
                ?.popBackStackImmediate()
        }
    }

    private fun addApartment() {
        if (areFieldsCorrect()) {
            if (isImageGiven()) {
//                val MY_READ_EXTERNAL_REQUEST : Int = 1
//                if (checkSelfPermission(
//                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_READ_EXTERNAL_REQUEST)
//                }
                sendApartment()
            }
        }
    }

    private fun sendApartment() {
        val url = MyApp.apiUrl +
                "Apartments/AddApartment"


//        val fileImage = File(imagesArray[0].picturePath)
//
//        val requestBody = fileImage.asRequestBody("image/*".toMediaTypeOrNull())

//        val filePart = MultipartBody.Part.createFormData("ApartmentImages", fileImage.name, requestBody)

        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("ApartmentName", rootView.add_apartment_name.text.toString())
            .addFormDataPart("ApartmentCity", rootView.add_apartment_city.text.toString())
            .addFormDataPart("ApartmentStreet", rootView.add_apartment_street.text.toString())
            .addFormDataPart(
                "ApartmentStreetNumber",
                rootView.add_apartment_street_number.text.toString()
            )
            .addFormDataPart("ApartmentZipCode", rootView.add_apartment_zip_code.text.toString())
            .addFormDataPart("ApartmentPrice", rootView.add_apartment_price.text.toString())
            .addFormDataPart(
                "ApartmentDescription",
                rootView.add_apartment_description.text.toString()
            )
//            .addFormDataPart("ApartmentImages", fileImage.name, requestBody)

        for (image in imagesArray) {
            val fileImage = File(image.picturePath)
            val requestBody = fileImage.asRequestBody("image/*".toMediaTypeOrNull())
            multipartBody.addFormDataPart("ApartmentImages", fileImage.name, requestBody)
        }

        val requestBody =  multipartBody.build()

//        for(image in imagesArray){
//
//            val fileImage = File(image.toString())
//
//            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), fileImage)
//
//            val filePart = MultipartBody.Part.createFormData("ApartmentImages", fileImage.name, requestBody)
//        }
////
//        val formBody = FormBody.Builder()
//            .add("ApartmentName", rootView.add_apartment_name.text.toString())
//            .add("ApartmentCity", rootView.add_apartment_city.text.toString())
//            .add("ApartmentStreet", rootView.add_apartment_street.text.toString())
//            .add("ApartmentStreetNumber", rootView.add_apartment_street_number.text.toString())
//            .add("ApartmentZipCode", rootView.add_apartment_zip_code.text.toString())
//            .add("ApartmentPrice", rootView.add_apartment_price.text.toString())
//            .add("ApartmentDescription", rootView.add_apartment_description.text.toString())
//            .build()

//        val imagesPart = MultipartBody.Builder()
//            .


        val request = Request.Builder()
            .url(url)
            .header("Content-Type", "multipart/form-data")
            .post(requestBody)
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
                                "toast_add_apartment_failure"
                            )
                        ),
                        Toast.LENGTH_SHORT
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
                                        "toast_add_apartment"
                                    )
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                            fragmentManager
                                ?.popBackStackImmediate()
                        }
                    }


                    else -> {

                    }
                }
            }
        }

        )

    }

    private fun isImageGiven(): Boolean {
        if (imagesArray.size == 0) {
            Toast.makeText(
                rootView.context, getString(
                    getStringIdentifier(
                        rootView.context,
                        "toast_add_image"
                    )
                ), Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun startGallery() {
        val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        cameraIntent.type = "image/*"
        if (cameraIntent.resolveActivity(activity!!.packageManager) != null) {
            startActivityForResult(cameraIntent, 1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                val returnUri = data?.data
                val bitmapImage =
                    MediaStore.Images.Media.getBitmap(activity!!.contentResolver, returnUri)
                val imagePath = ImageFilePath.getPath(rootView.context, data?.data)
                val picture = Picture(imagePath, bitmapImage)
                imagesArray.add(picture)
                addImageToLayout(bitmapImage)
            }
        }
    }

    private fun addImageToLayout(imageBitmap: Bitmap) {
        if (images_scroll_view.visibility == View.GONE) {
            images_scroll_view.visibility = View.VISIBLE
        }
        val image = ImageView(rootView.context)
        image.setImageBitmap(imageBitmap)
        images_linear_layout.addView(image)
        image.layoutParams.width = 240
        image.layoutParams.height = 240
    }

    private fun areFieldsCorrect(): Boolean {
        clearFieldErrors()
        var correct = areFieldsFilled()
        if (!correct) {
            return correct
        }

        correct = validationHolder.isZipCodeCorrect(rootView.add_apartment_zip_code.text.toString())
        if (!correct) {
            errorsHandler.setIncorrectZipCodeError(rootView.add_apartment_zip_code_layout)
        }

        return correct
    }

    private fun areFieldsFilled(): Boolean {
        var correct = true
        if (!validationHolder.isFieldFilled(rootView.add_apartment_name)) {
            correct = false
            errorsHandler.setEmptyFieldError(rootView.add_apartment_name_layout)
        }
        if (!validationHolder.isFieldFilled(rootView.add_apartment_city)) {
            correct = false
            errorsHandler.setEmptyFieldError(rootView.add_apartment_city_layout)
        }
        if (!validationHolder.isFieldFilled(rootView.add_apartment_zip_code)) {
            correct = false
            errorsHandler.setEmptyFieldError(rootView.add_apartment_zip_code_layout)
        }
        if (!validationHolder.isFieldFilled(rootView.add_apartment_street)) {
            correct = false
            errorsHandler.setEmptyFieldError(rootView.add_apartment_street_layout)
        }
        if (!validationHolder.isFieldFilled(rootView.add_apartment_street_number)) {
            correct = false
            errorsHandler.setEmptyFieldError(rootView.add_apartment_street_number_layout)
        }
        if (!validationHolder.isFieldFilled(rootView.add_apartment_price)) {
            correct = false
            errorsHandler.setEmptyFieldError(rootView.add_apartment_price_layout)
        }
        if (!validationHolder.isFieldFilled(rootView.add_apartment_description)) {
            correct = false
            errorsHandler.setEmptyFieldError(rootView.add_apartment_description_layout)
        }
        return correct
    }

    private fun clearFieldErrors() {
        errorsHandler.clearError(rootView.add_apartment_name_layout)
        rootView.add_apartment_name.clearFocus()
        errorsHandler.clearError(rootView.add_apartment_city_layout)
        rootView.add_apartment_city.clearFocus()
        errorsHandler.clearError(rootView.add_apartment_zip_code_layout)
        rootView.add_apartment_zip_code.clearFocus()
        errorsHandler.clearError(rootView.add_apartment_street_layout)
        rootView.add_apartment_street.clearFocus()
        errorsHandler.clearError(rootView.add_apartment_street_number_layout)
        rootView.add_apartment_street_number.clearFocus()
        errorsHandler.clearError(rootView.add_apartment_price_layout)
        rootView.add_apartment_price.clearFocus()
        errorsHandler.clearError(rootView.add_apartment_description_layout)
        rootView.add_apartment_description.clearFocus()
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
