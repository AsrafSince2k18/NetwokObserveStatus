package com.example.networkstatusbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.networkstatusbar.domain.repo.NetworkObserve
import com.example.networkstatusbar.presentance.component.NetworkStatusBar
import com.example.networkstatusbar.presentance.response.NetworkResponse
import com.example.networkstatusbar.ui.theme.NetworkStatusBarTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkObserve: NetworkObserve
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val networkStatus by networkObserve.networkObserve.collectAsState()
            NetworkStatusBarTheme {
                var messge by rememberSaveable {
                    mutableStateOf("")
                }
                var connected by rememberSaveable {
                    mutableStateOf(false)
                }
                var color by remember {
                    mutableStateOf(Color.Red)
                }
                LaunchedEffect(key1 = networkStatus) {
                    when(networkStatus){
                        NetworkResponse.Connected -> {
                            messge="Back to online"
                            color=Color.Green
                            delay(2000)
                            connected=false
                        }
                        NetworkResponse.DisConnected -> {
                            messge="No connection"
                            color=Color.Red
                            connected=true
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                    bottomBar = {
                        NetworkStatusBar(
                            isSelected =connected,
                            message =messge,
                            color =color
                        )
                    }) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NetworkStatusBarTheme {
        Greeting("Android")
    }
}