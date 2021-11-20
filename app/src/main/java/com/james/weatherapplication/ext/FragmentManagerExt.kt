package com.james.weatherapplication.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.addFragment(fragment: Fragment, frameId: Int) {
    beginTransaction().apply {
        add(frameId, fragment)
        commit()
    }
}