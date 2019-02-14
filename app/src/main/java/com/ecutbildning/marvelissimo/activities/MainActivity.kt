package com.ecutbildning.marvelissimo.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.ecutbildning.marvelissimo.fragments.CharacterSearchFragment
import com.ecutbildning.marvelissimo.fragments.ComicsSearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.SearchView
import android.support.v4.app.Fragment
import com.ecutbildning.marvelissimo.R
import android.util.Log
import android.widget.TextView
import com.ecutbildning.marvelissimo.dtos.User
import com.ecutbildning.marvelissimo.fragments.ISearchFragment
import com.ecutbildning.marvelissimo.services.FireBase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(top_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navigation.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)
        nav_view.setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, CharacterSearchFragment.newInstance())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu:Menu):Boolean {
        menuInflater.inflate(R.menu.top_navigation, menu)

        val currentUser = FireBase.currentUser
        findViewById<TextView>(R.id.user_email).text = currentUser?.email
        findViewById<TextView>(R.id.user_firstname).text = currentUser?.firstName
        findViewById<TextView>(R.id.user_lastname).text = currentUser?.lastName

        FireBase.loadOnlineUsers { documents ->
            for (document in documents) {
                val user = document.toObject(User::class.java)
                Log.d("MAIN_ACTIVITY", user.firstName + " " + user.lastName)
            }
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, top_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        drawer_layout.bringToFront()
        toggle.syncState()

        val searchItem = menu.findItem(R.id.navigation_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val currentFragment = getCurrentFragment() as ISearchFragment
                    currentFragment.makeSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun getCurrentFragment(): Fragment? {
        val fragments = supportFragmentManager.fragments
        fragments.forEach {fragment -> if(fragment.isVisible){return fragment}}
        return null
    }

    private val onBottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_characters -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            CharacterSearchFragment.newInstance()
                        )
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_comics -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            ComicsSearchFragment.newInstance()
                        )
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourites -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val onDrawerNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
           R.id.users -> {
               val userGroupsIsVisible = nav_view.menu.findItem(R.id.nav_online).isVisible
               nav_view.menu.findItem(R.id.nav_online).isVisible = !userGroupsIsVisible
               nav_view.menu.findItem(R.id.nav_offline).isVisible = !userGroupsIsVisible
            }
            R.id.logout -> {
                FireBase.signOut()
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
            }
            else -> drawer_layout.closeDrawer(GravityCompat.START)
        }
        return@OnNavigationItemSelectedListener true
    }

    override fun onStop() {
        FireBase.signOut()
        super.onStop()
    }
}
