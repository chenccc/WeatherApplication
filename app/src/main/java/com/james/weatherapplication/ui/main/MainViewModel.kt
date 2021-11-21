package com.james.weatherapplication.ui.main

import com.james.weatherapplication.base.BaseViewModel
import com.james.weatherapplication.utils.SingleLiveEvent

class MainViewModel: BaseViewModel() {
    val drawerOpenEvent = SingleLiveEvent<Unit>()
    val changeCityEvent = SingleLiveEvent<String>()
    val closeDrawerEvent = SingleLiveEvent<Unit>()
}