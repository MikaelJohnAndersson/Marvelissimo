package com.ecutbildning.marvelissimo.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.ecutbildning.marvelissimo.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mTopToolbar = findViewById<Toolbar>(R.id.top_toolbar)
        setSupportActionBar(mTopToolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu:Menu):Boolean {
        menuInflater.inflate(R.menu.top_navigation, menu)
        return true
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_characters -> {
                message.setText(R.string.title_characters)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_comics -> {
                message.setText(R.string.title_comics)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favourites -> {
                message.setText(R.string.title_favourites)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
