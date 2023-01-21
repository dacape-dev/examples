package dev.dacape.example.kotlin.deeplinksjetpackcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Home(name: String?) {
    Box(Modifier.padding(15.dp)){
        Column() {
            Text("Hello $name")
        }
    }
}