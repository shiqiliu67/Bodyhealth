package com.cs411cmp003.bodywatchfrontend.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.cs411cmp003.bodywatchfrontend.R
import com.cs411cmp003.bodywatchfrontend.databinding.ActivityMainBinding
import com.cs411cmp003.bodywatchfrontend.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

       // setUpObserve()
       // viewModel.startService(6777)

    }



    /**
     * Menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.goals -> {
                findNavController(R.id.nav_host_fragment_content_main).
                navigate(R.id.GoalsFragment)
                true
            }
            R.id.userProfile -> {
                findNavController(R.id.nav_host_fragment_content_main).
                navigate(R.id.UsersFragment)
                true
            }
            R.id.health -> {
                findNavController(R.id.nav_host_fragment_content_main).
                navigate(R.id.HealthFragment)
                true
            }
            R.id.activity -> {
                findNavController(R.id.nav_host_fragment_content_main).
                navigate(R.id.ActivitiesFragment)
                true
            }
            R.id.foodSuggestion -> {
                findNavController(R.id.nav_host_fragment_content_main).
                navigate(R.id.QueryFragment)
                true
            }
            R.id.ranking -> {
                findNavController(R.id.nav_host_fragment_content_main).
                navigate(R.id.RankingFragment)
                true
            }
            R.id.logout -> {
                logoutUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun logoutUser(){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("user", -1)
            apply()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}