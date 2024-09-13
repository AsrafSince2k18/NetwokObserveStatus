package com.example.networkstatusbar.di

import android.content.Context
import com.example.networkstatusbar.data.repo.NetworkObserveImpl
import com.example.networkstatusbar.domain.repo.NetworkObserve
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkObserveModule {

    @Provides
    @Singleton
    fun provideCoroutineScope():CoroutineScope{
        return CoroutineScope(SupervisorJob()+Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideNetworkObserveImpl(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
    ):NetworkObserve{
        return NetworkObserveImpl(context,coroutineScope)
    }

}