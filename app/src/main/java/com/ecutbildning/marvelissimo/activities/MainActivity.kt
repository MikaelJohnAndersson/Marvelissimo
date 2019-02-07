package com.ecutbildning.marvelissimo.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.fragments.CharacterSearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.Toolbar

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
                .add(R.id.container, CharacterSearchFragment.newInstance())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu:Menu):Boolean {
        menuInflater.inflate(R.menu.top_navigation, menu)
        return true
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_characters -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CharacterSearchFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_comics -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourites -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount == 0){
            super.onBackPressed()
        }
        else{
            fragmentManager.popBackStack()
        }
    }
}
