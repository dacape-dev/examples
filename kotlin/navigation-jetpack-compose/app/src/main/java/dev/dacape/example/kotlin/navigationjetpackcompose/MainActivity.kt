package dev.dacape.example.kotlin.navigationjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.dacape.example.kotlin.navigationjetpackcompose.navigation.AppNavigationBar
import dev.dacape.example.kotlin.navigationjetpackcompose.navigation.AppNavigationHost
import dev.dacape.example.kotlin.navigationjetpackcompose.ui.theme.NavigationJetpackComposeTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Main() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            AppNavigationBar(navController = navController)
        },
        content = { AppNavigationHost(navController = navController, modifier = Modifier.padding(it)) }
    )
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavigationJetpackComposeTheme {
        Main()
    }
}