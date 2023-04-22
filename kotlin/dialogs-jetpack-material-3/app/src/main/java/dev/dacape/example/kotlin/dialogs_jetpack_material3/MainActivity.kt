package dev.dacape.example.kotlin.dialogs_jetpack_material3

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.dacape.example.kotlin.dialogs_jetpack_material3.ui.theme.Dialogsjetpackmaterial3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Dialogsjetpackmaterial3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val owner = LocalViewModelStoreOwner.current

                    owner?.let {
                        val viewModel: MainViewModel = viewModel(
                            it,
                            "MainViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext
                                        as Application
                            )
                        )

                        Dialog(viewModel)
                    }


                }
            }
        }
    }
}

@Composable
fun Dialog(viewModel: MainViewModel) {

    Box(contentAlignment = Alignment.Center) {
        Button(
            onClick = { viewModel.onEvent(Event.OpenDialog) }
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Open dialog")
        }
    }


    if (viewModel.openDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(Event.CloseDialog)
            },
            icon = { Icon(Icons.Filled.Add, contentDescription = null) },
            title = {
                Text(text = "New item (example)")
            },
            text = {
                Text(
                    "Are you sure you want to add a new item? (example)"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(Event.CloseDialog)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(Event.CloseDialog)
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

