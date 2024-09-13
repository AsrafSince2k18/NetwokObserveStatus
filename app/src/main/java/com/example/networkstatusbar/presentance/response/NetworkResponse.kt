package com.example.networkstatusbar.presentance.response

sealed class NetworkResponse {

    data object Connected : NetworkResponse()
    data object DisConnected : NetworkResponse()

}