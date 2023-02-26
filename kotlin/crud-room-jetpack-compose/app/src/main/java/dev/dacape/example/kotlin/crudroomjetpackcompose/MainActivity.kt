package dev.dacape.example.kotlin.crudroomjetpackcompose

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.dacape.example.kotlin.crudroomjetpackcompose.db.model.Note
import dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model.Event
import dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model.NoteViewModel
import dev.dacape.example.kotlin.crudroomjetpackcompose.ui.model.NoteViewModelFactory
import dev.dacape.example.kotlin.crudroomjetpackcompose.ui.theme.CrudroomjetpackcomposeTheme
import java.util.*

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudScreenSetup(viewModel: NoteViewModel) {

    val all by viewModel.all.observeAsState(listOf())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.load(null)
                viewModel.onEvent(Event.OpenDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New note"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        CrudScreen(
            all = all,
            viewModel = viewModel,
            onEdit = {
                if(viewModel.isEdit()){
                    viewModel.onEvent(Event.Update)
                }else{
                    viewModel.onEvent(Event.Insert)
                }
                viewModel.onEvent(Event.CloseDialog)
            },
            onClose = {
                viewModel.onEvent(Event.CloseDialog)
            },
            onEvent = { viewModel.onEvent(it) }
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudScreen(
    all: List<Note>,
    viewModel: NoteViewModel,
    onEdit:()-> Unit,
    onClose:()-> Unit,
    onEvent: (Event)-> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(all){
                ListItem(
                    headlineText = { Text(it.text) },
                    supportingText = { Text(it.text) },
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp),
                    trailingContent = {
                        IconButton(onClick = {
                            it.id?.let { id -> viewModel.load(id) }
                        }){
                            Icon( Icons.Rounded.Edit, contentDescription = null)
                        }
                    }
                )
                Divider()
            }
        }
    }

    EditDialog(viewModel = viewModel, onEdit = onEdit, onEvent = onEvent)
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditDialog(viewModel: NoteViewModel, onEdit:()-> Unit, onEvent: (Event)-> Unit){

    if (viewModel.openDialog) {
        Dialog(
            onDismissRequest = { onEvent(Event.CloseDialog) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(
                        value = viewModel.text.value.text,
                        onValueChange = { onEvent(Event.SetText(it)) },
                        label = { Text("Text") }
                    )
                }

                Box(modifier = Modifier.padding(16.dp)) {
                    TextButton(
                        onClick = { onEvent(Event.CloseDialog) },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = onEdit,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CrudroomjetpackcomposeTheme {
        Crud()
    }
}