package com.e.truehomemobile.activityHolders.logic

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import com.e.truehomemobile.models.classes.LogicHolder
import kotlinx.android.synthetic.main.activity_language.*
import java.util.*

class LanguageLogicHolder(context: Context, activity : Activity): LogicHolder(context, activity){

    fun initActivity(){
        makeStartAnimations()

        activity.polish_linear_layout.setOnClickListener {
            setLocale("pl")
            activity.recreate()
        }
        activity.english_linear_layout.setOnClickListener {
            setLocale("en")
            activity.recreate()
        }

    }

    private fun makeStartAnimations(){
        animationHolder.fallFromTop(activity.logoImageView, 200, 20)
        animationHolder.popUp(activity.language_text_view, 300,40)
        animationHolder.popUp(activity.languages_scroll_view, 400, 50)
    }


    private fun setLocale(langCode: String) {
        val locale = Locale(langCode)
        val config = Configuration(context.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        context.resources.updateConfiguration(config,
            context.resources.displayMetrics
        )
    }
}