package com.stegner.androiddex.pokemonlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.stegner.androiddex.R

/**
 * Main acivity for the androidDex app. Holds the Navigation host fragment, drawer, toolbar, etc
 */
class PokemonListActivity : AppCompatActivity() {

    // This is the view that pop out when clicking on the hamburger menu
    private lateinit var drawerLayout: DrawerLayout

    // This is the three dot menu item on a specific fragment
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_activity)
        // builds the drawer menu
        setupNavigationDrawer()

        // append the toolbar to the appBarConfig
        setSupportActionBar(findViewById(R.id.toolbar))

        // get an instance of the navcontroller form the view
        val navController: NavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration.Builder(R.id.pokemonListFragment).setDrawerLayout(drawerLayout).build()

        // allows the title bar to be updated when navigating and use of the navigation
        // changes the menu three dot, to be in the context of the fragment
        setupActionBarWithNavController(navController, appBarConfiguration)

        // this ties together menu items and navigation
        // when a menu item is click it will navigate to that destination now
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
    }

    // This is specified because the programmer is responsible for the logic of navigate up when
    // we instantiated setupActionBarWithNavController
    override fun onSupportNavigateUp(): Boolean {
        return  findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigationDrawer(){
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.colorTextPrimary)
        }
    }
}
