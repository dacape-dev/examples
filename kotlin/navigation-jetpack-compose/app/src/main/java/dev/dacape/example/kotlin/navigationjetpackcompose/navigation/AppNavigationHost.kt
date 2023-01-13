package dev.dacape.example.kotlin.navigationjetpackcompose.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.dacape.example.kotlin.navigationjetpackcompose.Home1
import dev.dacape.example.kotlin.navigationjetpackcompose.Home2
import dev.dacape.example.kotlin.navigationjetpackcompose.Page1

@ExperimentalMaterial3Api
@Composable
fun AppNavigationHost(navController: NavHostController, modifier: Modifier) {

    NavHost(
        navController = navController,
        startDestination = AppNavigationBarItems.Home.route,
    ) {
        homeGraph(navController, modifier)
        pageGraph(navController, modifier)
    }
}


fun NavGraphBuilder.homeGraph(navController: NavController, modifier: Modifier) {
    navigation(startDestination = "home1", route = AppNavigationBarItems.Home.route) {
        composable("home1") {
            Home1(modifier, onNavigateToHome2 = {
                navController.navigate("home2")
            })
        }
        composable("home2") {
            Home2(modifier)
        }
    }
}

fun NavGraphBuilder.pageGraph(navController: NavController, modifier: Modifier) {
    navigation(startDestination = "page1", route = AppNavigationBarItems.Pages.route) {
        composable("page1") {
            Page1(modifier)
        }
    }
}