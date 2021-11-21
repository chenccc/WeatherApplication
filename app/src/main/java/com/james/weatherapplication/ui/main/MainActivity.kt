package com.james.weatherapplication.ui.main

import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import com.james.weatherapplication.R
import com.james.weatherapplication.base.BaseActivity
import com.james.weatherapplication.databinding.ActivityMainBinding
import com.james.weatherapplication.ui.drawer.DrawerFragment
import com.james.weatherapplication.ui.home.HomeFragment
import com.james.weatherapplication.ext.addFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.james.weatherapplication.ext.observe


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private lateinit var toggle: ActionBarDrawerToggle

    companion object {
        private const val TAG = "MainActivity"
    }

    private val mainViewModel: MainViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun getVM(): MainViewModel = mainViewModel

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun bindVM(binding: ActivityMainBinding, vm: MainViewModel) {
        with(vm) {
            binding.viewModel = this
            observe(closeDrawerEvent) {
                drawerLayout?.closeDrawer(Gravity.LEFT)
            }
        }
    }

    override fun setupView() {
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open,
            R.string.close
        ).apply {
            drawerLayout.addDrawerListener(this)
            syncState()
        }

        drawerLayout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                // notify DrawerFragment to update the history when drawer opens
                Log.d(TAG, "drawer open")
                mainViewModel.drawerOpenEvent.postValue(Unit)
            }

            override fun onDrawerClosed(drawerView: View) {
                Log.d(TAG, "drawer close")
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupFragments()
    }

    private fun setupFragments() {
        // add home fragment and drawer fragment
        supportFragmentManager.apply {
            val homeFragment = findFragmentById(R.id.fragment_container)
                as? HomeFragment

            if (homeFragment == null) {
                addFragment(HomeFragment.newInstance(), R.id.fragment_container)
            }

            val drawerFragment = findFragmentById(R.id.fragment_container_drawer_menu)
                as? DrawerFragment

            if (drawerFragment == null) {
                addFragment(DrawerFragment.newInstance(), R.id.fragment_container_drawer_menu)
            }
        }
    }
}