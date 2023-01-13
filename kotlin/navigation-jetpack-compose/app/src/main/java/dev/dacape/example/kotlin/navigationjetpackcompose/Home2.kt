package dev.dacape.example.kotlin.navigationjetpackcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Home2(modifier: Modifier) {
    Box(Modifier.padding(15.dp)){
        Column(modifier = modifier) {
            Text("Home2")
        }
    }
}