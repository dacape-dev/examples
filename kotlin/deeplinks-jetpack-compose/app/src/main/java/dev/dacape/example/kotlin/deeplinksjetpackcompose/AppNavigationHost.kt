package dev.dacape.example.kotlin.deeplinksjetpackcompose

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            deepLinks = listOf(navDeepLink {
                uriPattern = "https://www.dacape.dev/{name}"
                action = Intent.ACTION_VIEW
            }, navDeepLink {
                uriPattern = "dacapetest://www.dacape.dev/{name}"
                action = Intent.ACTION_VIEW
            }),
            arguments = listOf(navArgument("name") {
                type = NavType.StringType
                defaultValue = "Dacape"
            })
        ) {
            backStackEntry ->
                Home(backStackEntry.arguments?.getString("name"))
        }
    }
}
