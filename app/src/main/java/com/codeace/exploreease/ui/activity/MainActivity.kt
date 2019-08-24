package com.codeace.exploreease.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.codeace.exploreease.R
import com.codeace.exploreease.ui.fragments.BookmarkFragment
import com.codeace.exploreease.ui.fragments.CategoryFragment
import com.codeace.exploreease.ui.fragments.DiscoverFragment
import com.codeace.exploreease.ui.fragments.RecommendationFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.nav_layout.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var selectedFragment: Fragment? = null

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "GetUser")
        mAuth = FirebaseAuth.getInstance()
        getUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar_title.text = toolbar.title
        supportActionBar?.setDisplayShowTitleEnabled(false)

        statusBar.layoutParams.height = resources.getDimensionPixelSize(
            resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
        ).plus(toolbar.layoutParams.height)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        logOut.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            mAuth.signOut()
            drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
                override fun onDrawerOpened(drawerView: View) {}
                override fun onDrawerStateChanged(newState: Int) {}
                override fun onDrawerClosed(drawerView: View) {
                    getUser()
                }
            })
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.id_discover -> {
                    selectedFragment = DiscoverFragment()
                    bottomNavigationView.isSelected = true
                }
                R.id.id_popular -> {
                    selectedFragment = CategoryFragment()
                }
                R.id.id_recommendation -> {
                    selectedFragment = RecommendationFragment()
                }
                R.id.id_bookmark -> {
                    selectedFragment = BookmarkFragment()
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment, selectedFragment!!)
                .commit()
            true
        }
    }

    private fun getUser() {
        avatar.isVisible = false
        Glide.with(this).load("").centerCrop()
            .placeholder(R.drawable.imageplaceholder).into(avatar)
        mail.text = ""
        position.text = ""
        Log.d(TAG, "UserCleared")
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this, "Please Login/SignUp to use the app", Toast.LENGTH_LONG).show()
            finish()
            Log.d(TAG, "LoginRequest")
        } else {
            Log.d(TAG, "UpdateUI")
            updateUi(mAuth.currentUser!!)
        }
    }

    private fun updateUi(currentUser: FirebaseUser) {
        avatar.isVisible = true
        Glide.with(this).load(currentUser.photoUrl.toString()).centerCrop()
            .placeholder(R.drawable.ic_user).into(avatar)
        mail.text = currentUser.displayName
        position.text = currentUser.email
        Log.d(TAG, "UserAdded")

        Log.d(TAG, "View Model Initialize")
//        initViewModel()
    }
}