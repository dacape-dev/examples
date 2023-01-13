package dev.dacape.example.kotlin.navigationjetpackcompose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Home1(modifier: Modifier, onNavigateToHome2: () -> Unit) {
    Column(modifier = modifier) {
        Text("Home1")
        Button(onClick = onNavigateToHome2) {
            Text("Go Home2")
        }
    }
}