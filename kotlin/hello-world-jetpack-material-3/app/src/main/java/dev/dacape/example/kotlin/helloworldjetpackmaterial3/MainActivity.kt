package dev.dacape.example.kotlin.helloworldjetpackmaterial3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.dacape.example.kotlin.helloworldjetpackmaterial3.ui.theme.HelloWorldTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloWorldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HelloWorldWithTextField()
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun HelloWorldWithTextField() {

    var text by remember {
        mutableStateOf(TextFieldValue("Daniel", TextRange(0, 7)))
    }

    Column(modifier = Modifier.padding(10.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            label = { Text("Your name") }
        )

        Box(modifier = Modifier.fillMaxWidth()){
            Text(text = "Hello ${text.text}!", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloWorldTheme {
        HelloWorldWithTextField()
    }
}