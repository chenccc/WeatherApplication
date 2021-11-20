package com.james.weatherapplication.ui

import android.view.MenuItem
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

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private lateinit var toggle: ActionBarDrawerToggle

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

    override fun bindVM(binding: ActivityMainBinding, vm: MainViewModel) = Unit

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupFragments()
    }

    private fun setupFragments() {
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