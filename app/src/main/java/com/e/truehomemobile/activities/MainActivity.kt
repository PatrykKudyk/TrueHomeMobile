package com.e.truehomemobile.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.e.truehomemobile.R
import com.e.truehomemobile.fragments.ApartmentListFragment
import com.e.truehomemobile.fragments.LanguageFragment
import com.e.truehomemobile.fragments.LoginFragment
import com.e.truehomemobile.fragments.RegistrationFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ApartmentListFragment.OnFragmentInteractionListener,
    LanguageFragment.OnFragmentInteractionListener,
    LoginFragment.OnFragmentInteractionListener,
    RegistrationFragment.OnFragmentInteractionListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var apartmentListFragment: ApartmentListFragment
    lateinit var languageFragment: LanguageFragment
    lateinit var loginFragment: LoginFragment
    lateinit var registrationFragment: RegistrationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        apartmentListFragment = ApartmentListFragment.newInstance()
        languageFragment = LanguageFragment.newInstance()
        loginFragment = LoginFragment.newInstance("param 1", "param 2")
        registrationFragment = RegistrationFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout, apartmentListFragment)
            .commit()
//        apartmentListFragment.initFragment()

//        loginFragment.login_button.setOnClickListener {
////            MyApp.isLogged = false                   // USUNĄĆ TO JAK JUŻ BĘDZIE LOGOWANIE
////            clearFieldsErrors()
////            if(areFieldsFilled()){
////                if(checkUserDataCorrectness()){
////                    login_field.text = null
////                    password_field.text = null
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, registrationFragment)
//                        .addToBackStack(registrationFragment.toString())
//                        .commit()
////                }
////            }
//        }
//
//        registrationFragment.register_button.setOnClickListener {
//            finish()
//        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_apartments -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, apartmentListFragment)
                    .commit()
//                apartmentListFragment.initFragment()
            }
            R.id.menu_language -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, languageFragment)
                    .commit()
            }
            R.id.menu_account -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, loginFragment)
                    .commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
