package com.aksstore.storily

import android.content.res.Resources.Theme
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.aksstore.storily.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var toolbar: androidx.appcompat.widget.Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_share,
            R.id.nav_contact_us,
            R.id.nav_about
            // Add more destination IDs as needed
        ), binding.drawerLayout)

        init()
    }



    fun init() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_home_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.story_nav)
        navController.graph = graph

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navView, navController)


        toolbar = findViewById(R.id.toolbar)
        toolbar?.setNavigationIcon(R.drawable.ic_drawer_icon)

        supportActionBar?.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_drawer_icon, null))

        if (toolbar != null) {
            toolbar?.setNavigationOnClickListener {
                if (supportActionBar?.title == "Home") {
                    findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(
                        GravityCompat.START)
                } else {
                    navController.navigateUp()
                }

            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        try {

            navController.addOnDestinationChangedListener { _, destination, _ ->

                when (destination.id) {

                    R.id.homeFragment -> {
                        supportActionBar?.apply {
                            this.title = "Home"
                            setDisplayHomeAsUpEnabled(true)
                            setHomeAsUpIndicator(R.drawable.ic_drawer_icon)
                        }
                    }

                    R.id.storiesListFragment -> {
                        supportActionBar?.apply {
                            this.title = "Stories List"
                            setDisplayHomeAsUpEnabled(true)
                            setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
                        }
                    }

                    R.id.readStoriesFragment -> {
                        supportActionBar?.apply {
                            this.title = "Read story"
                            setDisplayHomeAsUpEnabled(true)
                            setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("MainActivity", "init: $e")
        }
    }
}