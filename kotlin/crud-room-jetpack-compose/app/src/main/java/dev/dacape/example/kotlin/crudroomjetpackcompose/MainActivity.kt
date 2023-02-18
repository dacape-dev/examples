package dev.dacape.example.kotlin.crudroomjetpackcompose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note
import dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model.NoteViewModel
import dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model.NoteViewModelFactory
import dev.dacape.example.kotlin.crudroomjetpackcompose.ui.theme.CrudroomjetpackcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrudroomjetpackcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Crud()
                }
            }
        }
    }
}

@Composable
fun Crud() {
    val owner = LocalViewModelStoreOwner.current

    owner?.let {
        val viewModel: NoteViewModel = viewModel(
            it,
            "NoteViewModel",
            NoteViewModelFactory(
                LocalContext.current.applicationContext
                        as Application
            )
        )

        CrudScreenSetup(viewModel)
    }
}

@Composable
fun CrudScreenSetup(viewModel: NoteViewModel) {

    val all by viewModel.all.observeAsState(listOf())

    CrudScreen(
        all = all,
        viewModel = viewModel
    )
}

@Composable
fun CrudScreen(
    all: List<Note>,
    viewModel: NoteViewModel
) {

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CrudroomjetpackcomposeTheme {
        Crud()
    }
}