package dev.dacape.example.kotlin.navigationjetpackcompose.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.dacape.example.kotlin.navigationjetpackcompose.Home
import dev.dacape.example.kotlin.navigationjetpackcompose.Page1

@ExperimentalMaterial3Api
@Composable
fun AppNavigationHost(navController: NavHostController, modifier: Modifier) {

    NavHost(
        navController = navController,
        startDestination = AppNavigationBarItems.Home.route,
    ) {
        composable(AppNavigationBarItems.Home.route) {
            Home(modifier)
        }

        composable(AppNavigationBarItems.Page1.route) {
            Page1(modifier)
        }
    }
}