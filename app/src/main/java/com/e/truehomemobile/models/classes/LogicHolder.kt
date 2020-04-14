package com.e.truehomemobile.models.classes

import android.app.Activity
import android.content.Context
import com.e.truehomemobile.activityHolders.AnimationsHolder
import com.e.truehomemobile.activityHolders.ErrorsHandler
import com.e.truehomemobile.activityHolders.JsonHolder
import com.e.truehomemobile.activityHolders.ValidationHolder

open class LogicHolder(private val context: Context, private val activity: Activity) {

    protected val animationHolder = AnimationsHolder(context)
    protected val validationHolder = ValidationHolder()
    protected val errorsHolder = ErrorsHandler(context)
    protected val jsonHolder = JsonHolder()
}