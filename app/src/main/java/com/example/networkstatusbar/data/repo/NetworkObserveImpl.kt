package com.example.networkstatusbar.data.repo

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.networkstatusbar.domain.repo.NetworkObserve
import com.example.networkstatusbar.presentance.response.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

class NetworkObserveImpl(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
) : NetworkObserve{

    private val networkManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _networkState = getObserve().stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(5000),
        NetworkResponse.DisConnected
    )


    private fun getObserve():Flow<NetworkResponse>{
        return callbackFlow {
            val collectNetworkStatus = object :NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(NetworkResponse.Connected)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(NetworkResponse.DisConnected)
                }
            }
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

            networkManager.registerNetworkCallback(networkRequest,collectNetworkStatus)

            awaitClose {
                networkManager.unregisterNetworkCallback(collectNetworkStatus)
            }
        }.distinctUntilChanged()
    }

    override val networkObserve: StateFlow<NetworkResponse>
        get() = _networkState

}