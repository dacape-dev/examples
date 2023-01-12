package dev.dacape.example.kotlin.navigationjetpackcompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavBarItems(val route: String, val label: String, val icon: ImageVector) {
    object Home : NavBarItems("home", "Home", Icons.Filled.Home)
    object Page1 : NavBarItems("page1", "Page1", Icons.Filled.Star)
}