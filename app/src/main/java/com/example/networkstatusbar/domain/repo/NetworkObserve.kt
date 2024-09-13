package com.example.networkstatusbar.domain.repo

import com.example.networkstatusbar.presentance.response.NetworkResponse
import kotlinx.coroutines.flow.StateFlow

interface NetworkObserve {

    val networkObserve : StateFlow<NetworkResponse>

}