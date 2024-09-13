package com.example.networkstatusbar.presentance.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NetworkStatusBar(
    isSelected : Boolean,
    message:String,
    color: Color
) {
    AnimatedVisibility(visible = isSelected,
        enter = slideInVertically(tween(600)){h->h},
        exit = slideOutVertically(tween(600)){h->h}
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(4.dp),
            contentAlignment = Alignment.Center){
            Text(text = message,
                color = Color.White)
        }
    }
}