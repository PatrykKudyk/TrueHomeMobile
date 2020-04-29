package com.e.truehomemobile.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.e.truehomemobile.R
import com.e.truehomemobile.adapters.ApartmentListAdapter
import com.e.truehomemobile.models.apartment.Apartment
import com.e.truehomemobile.models.classes.MarginItemDecoration
import kotlinx.android.synthetic.main.fragment_apartment_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ApartmentListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ApartmentListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApartmentListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var rootView: View
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_apartment_list, container, false);
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
         * @return A new instance of fragment ApartmentListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ApartmentListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    fun initFragment(){
        recyclerView = rootView.findViewById(R.id.apartment_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(
            MarginItemDecoration(
                12
            )
        )
        recyclerView.adapter = ApartmentListAdapter(initApartmentList())
    }


    private fun initApartmentList(): ArrayList<Apartment>{               // metoda testowa, bez bazy
        val apartment1 = Apartment(0, "Dobry apartment", "Wroclaw",
            "Bzowa", "21", "37a", "51-132",
            300,"Opis apartamentu, który jest właśnie opisywany bla bla" +
                    "bla bla bla")
        val apartmentList = ArrayList<Apartment>()
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)
        apartmentList.add(apartment1)

        return apartmentList
    }
}
