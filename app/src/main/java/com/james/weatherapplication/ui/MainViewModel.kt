package com.james.weatherapplication.ui

import com.james.weatherapplication.base.BaseViewModel
import com.james.weatherapplication.utils.SingleLiveEvent

class MainViewModel: BaseViewModel() {
    val drawerOpenEvent = SingleLiveEvent<Unit>()
}