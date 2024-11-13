package com.example.fragmentdatasharing

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navigationView:NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.my_drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //Default Fragment
        replaceFragment(AccountFragment())

        navigationView.setNavigationItemSelectedListener { menuItem ->

            for (i in 0 until navigationView.menu.size()) {
                val item = navigationView.menu.getItem(i)
                item.isChecked = false
                item.title = SpannableString(item.title).apply {
                    setSpan(StyleSpan(Typeface.NORMAL), 0, length, 0)
                    setSpan(ForegroundColorSpan(Color.parseColor("#808080")), 0, length, 0)
                }
            }

            // Mark the clicked item as selected
            menuItem.isChecked = true
            menuItem.title = SpannableString(menuItem.title).apply {
                setSpan(StyleSpan(Typeface.BOLD), 0, length, 0)
                setSpan(ForegroundColorSpan(Color.RED), 0, length, 0)
            }
            //menuItem.isChecked = true
            when (menuItem.itemId) {


                R.id.nav_account -> {
                    replaceFragment(AccountFragment())
                }
                R.id.nav_settings -> {
                    replaceFragment(SettingFragment())

                }
                R.id.nav_logout -> {
                    Toast.makeText(applicationContext,"LogOut", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                }

            }
            true
        }.also {
            drawerLayout.closeDrawer(GravityCompat.START) // Close the drawer after selection
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(actionBarDrawerToggle.onOptionsItemSelected(item)){

            true
        }else onOptionsItemSelected(item)
    }


    private fun replaceFragment(fragment:Fragment){
        val replaceFragment:FragmentTransaction = supportFragmentManager.beginTransaction()
        replaceFragment.replace(R.id.frameLayout,fragment)
        replaceFragment.commit()
        drawerLayout.closeDrawers()
    }
}