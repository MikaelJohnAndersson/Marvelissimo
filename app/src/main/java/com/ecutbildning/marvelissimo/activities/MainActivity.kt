package com.ecutbildning.marvelissimo.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.ecutbildning.marvelissimo.fragments.CharacterSearchFragment
import com.ecutbildning.marvelissimo.fragments.ComicsSearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.SearchView
import android.support.v4.app.Fragment
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.fragments.ISearchFragment
import com.ecutbildning.marvelissimo.services.FireBase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(top_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navigation.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, CharacterSearchFragment.newInstance())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu:Menu):Boolean {
        menuInflater.inflate(R.menu.top_navigation, menu)

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

    override fun onStop() {
        FireBase.signOut()
        super.onStop()
    }
}
