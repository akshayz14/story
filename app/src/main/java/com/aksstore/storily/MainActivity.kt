package com.aksstore.storily

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.aksstore.storily.databinding.ActivityMainBinding
import com.aksstore.storily.model.Story
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var toolbar: androidx.appcompat.widget.Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_contact_us,
                R.id.nav_about
            ), binding.drawerLayout
        )

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
        toolbar?.setNavigationIcon(R.drawable.ic_drawer_ham_2)

        if (toolbar != null) {
            toolbar?.setNavigationOnClickListener {
                if (supportActionBar?.title == "Home") {
                    findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(
                        GravityCompat.START
                    )
                } else {
                    navController.navigateUp()
                }

            }
        }

        binding.navView.setNavigationItemSelectedListener {
            findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(
                GravityCompat.START
            )
            //TODO : Add app share dialog code below
//            if (it.itemId == R.id.nav_share) {
//                showAppShareDialog()
//            }

            if (it.itemId == R.id.nav_contact_us) {
                showContactUsDialog()
            }
            if (it.itemId == R.id.nav_about) {
                showAboutUsDialog()
            }
            false
        }

        binding.tvVersion.text = "Version : " + "v" + BuildConfig.VERSION_NAME
    }

    private fun showAboutUsDialog() {
        val dialog = Dialog(this, R.style.Theme_Dialog)
        dialog.setContentView(R.layout.custom_dialog)

        val tvDialogTitle = dialog.findViewById<TextView>(R.id.tvDialogTitle)
        val tvDialogDescription = dialog.findViewById<TextView>(R.id.tvDialogDescription)

        tvDialogTitle.text = resources.getString(R.string.about_us)
        tvDialogDescription.text = resources.getString(R.string.about_us_description)

        val closeButton = dialog.findViewById<Button>(R.id.dialogButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showContactUsDialog() {
        val dialog = Dialog(this, R.style.Theme_Dialog)
        dialog.setContentView(R.layout.custom_dialog)

        val tvDialogTitle = dialog.findViewById<TextView>(R.id.tvDialogTitle)
        val tvDialogDescription = dialog.findViewById<TextView>(R.id.tvDialogDescription)

        tvDialogTitle.text = resources.getString(R.string.contact_us)
        tvDialogDescription.text = resources.getString(R.string.storily_email)

        val closeButton = dialog.findViewById<Button>(R.id.dialogButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showAppShareDialog() {
        //TODO : Add app share here in future
    }

    override fun onResume() {
        super.onResume()
        try {

            navController.addOnDestinationChangedListener { _, destination, bundle ->

                when (destination.id) {

                    R.id.homeFragment -> {
                        supportActionBar?.apply {
                            this.title = "Home"
                            setDisplayHomeAsUpEnabled(true)
                            setHomeAsUpIndicator(R.drawable.ic_drawer_ham_2)
                        }
                    }

                    R.id.storiesListFragment -> {
                        var currentTopic: String? = null
                        bundle?.let {
                            currentTopic = it.getString("moduleName")
                        }

                        val modifiedCurrentTopic =
                            currentTopic?.replace("_", " ")  // Replace underscore with space
                                ?.lowercase()                                       // Convert to lowercase
                                ?.split(" ")                                        // Split into words
                                ?.joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }

                        supportActionBar?.apply {
                            this.title = modifiedCurrentTopic
                            setDisplayHomeAsUpEnabled(true)
                            setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
                        }
                    }

                    R.id.readStoriesFragment -> {
                        var currentStory: Story? = null
                        bundle?.let {
                            currentStory = it.getSerializable("story") as Story
                        }
                        supportActionBar?.apply {
                            if (currentStory != null) {
                                this.title = currentStory?.story_title
                            } else {
                                this.title = "Read story"
                            }
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