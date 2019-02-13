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
import android.support.v7.widget.Toolbar
import android.support.v7.widget.SearchView
import android.support.v4.app.Fragment
import com.ecutbildning.marvelissimo.R
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.ecutbildning.marvelissimo.fragments.ISearchFragment
import com.ecutbildning.marvelissimo.services.FireBase




class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(top_toolbar)
        top_toolbar.bringToFront()

        supportActionBar?.setDisplayShowTitleEnabled(false)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if(savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .add(R.id.container, CharacterSearchFragment.newInstance())
                .commit()
        }

    }

    override fun onStop() {
        FireBase.signOut()
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu:Menu):Boolean {
        menuInflater.inflate(R.menu.top_navigation, menu)
        menuInflater.inflate(R.menu.navigation_drawer, menu)

        val user = FireBase.currentUser
        val userEmail: TextView = findViewById(R.id.userEmail)
        userEmail.text = user?.email
        val userName: TextView = findViewById(R.id.userName)
        userName.text = "${user?.firstName} ${user?.lastName}"

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, top_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        drawer_layout.bringToFront()
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

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


   /*
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.friend_list -> {

            }
            R.id.logout -> {
            FireBase.signOut()
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
