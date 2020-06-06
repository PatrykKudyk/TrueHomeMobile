package com.e.truehomemobile.fragments.account

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.truehomemobile.MyApp

import com.e.truehomemobile.R
import com.e.truehomemobile.fragments.apartment.AddApartmentFragment
import com.e.truehomemobile.fragments.apartment.MyApartmentsFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.nav_header.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccountFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var navigationView: NavigationView
    private lateinit var rootView: View
    private lateinit var addButton: View
    private lateinit var logoutButton: View
    private lateinit var myApartmentsButton: View
    private lateinit var userDetailsButton: View
    private lateinit var addApartmentFragment: AddApartmentFragment
    private lateinit var myApartmentsFragment: MyApartmentsFragment
    private lateinit var showUserInfoFragment: ShowUserInfoFragment

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
        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        initFragment()
        return rootView
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
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AccountFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initFragment() {
        addButton = rootView.findViewById(R.id.add_apartment_button_account)
        logoutButton = rootView.findViewById(R.id.logout_text_view)
        myApartmentsButton = rootView.findViewById(R.id.show_my_apartments_button)
        userDetailsButton = rootView.findViewById(R.id.user_details_button)

        rootView.user_login_text_view.text = MyApp.userLogin

        myApartmentsButton.setOnClickListener {
            myApartmentsFragment = MyApartmentsFragment.newInstance()
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.frame_layout, myApartmentsFragment)
                ?.addToBackStack(MyApartmentsFragment.toString())
                ?.commit()
        }

        userDetailsButton.setOnClickListener {
            showUserInfoFragment = ShowUserInfoFragment.newInstance()
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.frame_layout, showUserInfoFragment)
                ?.addToBackStack(ShowUserInfoFragment.toString())
                ?.commit()
        }

        addButton.setOnClickListener {
            addApartmentFragment = AddApartmentFragment.newInstance()
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.frame_layout, addApartmentFragment)
                ?.addToBackStack(RegistrationFragment.toString())
                ?.commit()
        }

        logoutButton.setOnClickListener {
            MyApp.isLogged = false
            MyApp.token = ""
            MyApp.refreshToken = ""
            navigationView = activity?.findViewById(R.id.nav_view) as NavigationView
            navigationView.user_name_text_view.text = ""
            val menu = navigationView.menu
            menu.findItem(R.id.menu_account).setTitle(R.string.menu_account)
            navigationView.setCheckedItem(menu.findItem(R.id.menu_apartments))
            activity?.recreate()
        }

    }
}
