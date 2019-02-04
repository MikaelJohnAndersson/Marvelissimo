package com.ecutbildning.marvelissimo.activities

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.fragments.CharacterSearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CharacterSearchFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onFragmentInteraction(uri: Uri) {
        //Implement
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_characters -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, CharacterSearchFragment.newInstance())
                transaction.commit()
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
