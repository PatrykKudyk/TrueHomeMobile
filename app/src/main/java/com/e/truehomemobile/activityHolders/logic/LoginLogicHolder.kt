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
import com.e.truehomemobile.models.Request.LoginRequest
import kotlinx.android.synthetic.main.activity_login.*
import java.io.IOException
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody

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
            activity.login_field.text = null
            activity.password_field.text = null
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

    private fun checkUserDataCorrectness: Boolean{
        val loginRequest = LoginRequest(activity.login_field.text.toString(),
            activity.password_field.text.toString())
        fetchApiLoginResponse(loginRequest)
    }

    private fun fetchApiLoginResponse(loginRequest: LoginRequest){
        val url = MyApp.apiUrl + "security/login"

        val requestBody: RequestBody = jsonHolder.createLoginRequestJson(loginRequest).toRequestBody()


        val request = Request.Builder()
            .method("POST", requestBody)
            .url(url)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val rentals = gson.fromJson(body, Array<Rental>::class.java)

                activity.runOnUiThread{
                    rental_list_recycler_view_user.adapter = RentalListAdapter(rentals)
                }
            }
        })

    }

}