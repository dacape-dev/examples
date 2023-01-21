package dev.dacape.example.kotlin.deeplinksjetpackcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink

@Composable
fun AppNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home/{name}"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            "home/{name}",
            deepLinks = listOf(navDeepLink { uriPattern = "www.example.com/{name}" })
        ) {
            backStackEntry ->
                Home(backStackEntry.arguments?.getString("name"))
        }
    }
}
