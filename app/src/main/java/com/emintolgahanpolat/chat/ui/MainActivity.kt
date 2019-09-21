package com.emintolgahanpolat.chat.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.emintolgahanpolat.chat.R
import com.emintolgahanpolat.chat.ui.base.setupWithNavController
import android.view.MenuItem
import com.emintolgahanpolat.mytoolbar.AsyncTaskLoadImage
import com.emintolgahanpolat.mytoolbar.BitmapHelper
import com.emintolgahanpolat.mytoolbar.ImageDownloaderListener


class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupActionBar()
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupActionBar()
        setupBottomNavigationBar()


    }

    private fun setupActionBar() {

        supportActionBar!!.setDisplayShowHomeEnabled(true)

        AsyncTaskLoadImage(object : ImageDownloaderListener {
            override fun loaded(bm: Bitmap) {
                supportActionBar!!.setIcon(
                    BitmapDrawable(
                        resources,
                        BitmapHelper.circualBitmap(bm)
                    )
                )
            }

            override fun failed() {
                supportActionBar!!.setIcon(
                    BitmapDrawable(
                        resources, BitmapHelper.circualBitmap(
                            BitmapFactory.decodeResource(resources, R.drawable.empty_profile)
                        )
                    )
                )
            }
        }).execute("https://randomuser.me/api/portraits/women/79.jpg")
    }



    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(
            R.navigation.nav_chat_list,
            R.navigation.nav_person_list,
            R.navigation.nav_discovery
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}