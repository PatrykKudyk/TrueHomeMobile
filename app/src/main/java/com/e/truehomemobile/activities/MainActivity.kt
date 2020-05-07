package com.e.truehomemobile.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isEmpty
import androidx.drawerlayout.widget.DrawerLayout
import com.e.truehomemobile.MyApp
import com.e.truehomemobile.R
import com.e.truehomemobile.fragments.ApartmentListFragment
import com.e.truehomemobile.fragments.LanguageFragment
import com.e.truehomemobile.fragments.LoginFragment
import com.e.truehomemobile.fragments.RegistrationFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*


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
        loginFragment = LoginFragment.newInstance()

        if(!MyApp.hasAppStarted){
            supportFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout, apartmentListFragment)
            .commit()
            MyApp.hasAppStarted = true
        }

        if(MyApp.isLogged){
            val navigationView = findViewById<NavigationView>(R.id.nav_view)
            val menu = navigationView.menu
            menu.findItem(R.id.menu_account).setTitle(R.string.menu_account)
        }else{
            val navigationView = findViewById<NavigationView>(R.id.nav_view)
            val menu = navigationView.menu
            menu.findItem(R.id.menu_account).setTitle(R.string.menu_login)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_apartments -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, apartmentListFragment)
                    .commit()
            }
            R.id.menu_language -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, languageFragment)
                    .commitAllowingStateLoss()
            }
            R.id.menu_account -> {
                if(!MyApp.isLogged){
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, loginFragment)
                        .commit()
                }else{

                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
