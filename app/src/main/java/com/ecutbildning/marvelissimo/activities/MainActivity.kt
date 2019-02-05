package com.ecutbildning.marvelissimo.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.fragments.CharacterSearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CharacterSearchFragment.newInstance())
            .commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_characters -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CharacterSearchFragment.newInstance())
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
}
