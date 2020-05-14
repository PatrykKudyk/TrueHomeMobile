package com.e.truehomemobile.fragments.apartment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.e.truehomemobile.R
import com.e.truehomemobile.activityHolders.ErrorsHandler
import com.e.truehomemobile.activityHolders.ValidationHolder
import kotlinx.android.synthetic.main.fragment_add_apartment.view.*

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
    private lateinit var addButton: View
    private lateinit var backButton: View

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
        errorsHandler = ErrorsHandler(rootView.context)

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

        }
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
}
