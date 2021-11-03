package com.example.studymytaskapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.studymytaskapp.databinding.ActivityMainBinding
import com.example.studymytaskapp.databinding.NavHeaderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setupNavigation()
        setupDrawerHeader()
    }

    /**
     * To use binding on Drawer header
     */
    private fun setupDrawerHeader() {
        val bind = NavHeaderBinding.inflate(layoutInflater, binding.navView, false).also {
            it.lifecycleOwner = this
        }
        binding.navView.addHeaderView(bind.root)
    }

    /**
     * Called when hamburger button is tapped or back button is pressed
     *
     * Delegate to Navigation.
     */
    override fun onSupportNavigateUp(): Boolean {

        //MainActivity doesn't have NavController?
        //navController = this.findNavController(R.id.nav_host_fragment)
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    /**
     * Setup Navigation for Activity
     */
    private fun setupNavigation() {
        setSupportActionBar(binding.toolbar)

        //val navController = this.findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}