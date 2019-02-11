package com.ecutbildning.marvelissimo.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.ecutbildning.marvelissimo.fragments.CharacterSearchFragment
import com.ecutbildning.marvelissimo.fragments.ComicsSearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.Toolbar
import android.support.v7.widget.SearchView
import android.support.v4.app.Fragment
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.fragments.ISearchFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mTopToolbar = findViewById<Toolbar>(R.id.top_toolbar)
        setSupportActionBar(mTopToolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if(savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .add(R.id.container, CharacterSearchFragment.newInstance(), CharacterSearchFragment()::class.java.name)
                .addToBackStack(CharacterSearchFragment()::class.java.name)
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
        val fragmentTag = supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name
        return supportFragmentManager.findFragmentByTag(fragmentTag)
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_characters -> {
                if(getCurrentFragment()!!::class.java.name != CharacterSearchFragment()::class.java.name ){
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            CharacterSearchFragment.newInstance(),
                            CharacterSearchFragment()::class.java.name
                        )
                        .commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_comics -> {
                if(getCurrentFragment()!!::class.java.name != ComicsSearchFragment()::class.java.name ) {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            ComicsSearchFragment.newInstance(),
                            ComicsSearchFragment()::class.java.name
                        )
                        .commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourites -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

   /* override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount == 0){
            super.onBackPressed()
        }
        else{
            fragmentManager.popBackStack()
        }
    }*/
}
