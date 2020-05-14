package com.e.truehomemobile.fragments.extras

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.truehomemobile.MyApp

import com.e.truehomemobile.R
import com.google.android.material.navigation.NavigationView
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LanguageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LanguageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LanguageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var rootView: View
    private lateinit var navigationView: NavigationView

    private lateinit var polishCardView: View
    private lateinit var englishCardView: View
    private lateinit var polishLinearLayout: View
    private lateinit var englishLinearLayout: View

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
        rootView = inflater.inflate(R.layout.fragment_language, container, false);
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
     * activity.
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
         * @return A new instance of fragment LanguageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LanguageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    private fun initFragment() {
        polishCardView = rootView.findViewById(R.id.polish_card_view)
        englishCardView = rootView.findViewById(R.id.english_card_view)

        polishLinearLayout = rootView.findViewById(R.id.polish_linear_layout)
        englishLinearLayout = rootView.findViewById(R.id.english_linear_layout)

        when (MyApp.language) {
            "pl" -> {
                polishLinearLayout.setBackgroundColor(R.color.colorPrimary)
            }
            "en" -> {
                englishLinearLayout.setBackgroundColor(R.color.colorPrimary)
            }
            else -> {

            }
        }

        polishCardView.setOnClickListener {
            if (MyApp.language != "pl") {
                MyApp.language = "pl"
                setLocale("pl")
            }
        }
        englishCardView.setOnClickListener {
            if (MyApp.language != "en") {
                MyApp.language = "en"
                setLocale("en")
            }
        }

    }

    private fun setLocale(langCode: String) {
        val locale = Locale(langCode)
        val config = Configuration(resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )

        navigationView = activity?.findViewById(R.id.nav_view) as NavigationView
        val menu = navigationView.menu
        menu.findItem(R.id.menu_account).setTitle(R.string.menu_account)
        navigationView.setCheckedItem(menu.findItem(R.id.menu_apartments))
        activity?.recreate()
    }
}
