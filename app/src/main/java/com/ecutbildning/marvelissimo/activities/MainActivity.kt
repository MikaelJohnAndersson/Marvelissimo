package com.ecutbildning.marvelissimo.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.support.v7.widget.SearchView
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.fragments.CharacterSearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mTopToolbar = findViewById<Toolbar>(R.id.navigation_search)
        setSupportActionBar(mTopToolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CharacterSearchFragment.newInstance())
            .commit()
    }

    override fun onCreateOptionsMenu(menu:Menu):Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        val id = item.itemId
        if (id == R.id.navigation_search)
        {
            Toast.makeText(this@MainActivity, "Action clicked", Toast.LENGTH_LONG).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.navigation,menu)
//        val searchItem = menu.findItem(R.id.navigation_search)
//        if (searchItem != null){
//            val searchView = searchItem.actionView as SearchView
//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    return true
//                }
//            })
//        }
//        return super.onCreateOptionsMenu(menu)
//    }

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
