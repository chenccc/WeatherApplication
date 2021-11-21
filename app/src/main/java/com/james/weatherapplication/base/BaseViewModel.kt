package com.james.weatherapplication.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.james.weatherapplication.utils.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class BaseViewModel: ViewModel() {
    val errorMessage = SingleLiveEvent<String>()
}