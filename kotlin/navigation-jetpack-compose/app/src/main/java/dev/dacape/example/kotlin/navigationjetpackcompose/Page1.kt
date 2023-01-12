package dev.dacape.example.kotlin.navigationjetpackcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Page1(modifier: Modifier) {
    Box(modifier = modifier) {
        Text("Page1")
    }
}