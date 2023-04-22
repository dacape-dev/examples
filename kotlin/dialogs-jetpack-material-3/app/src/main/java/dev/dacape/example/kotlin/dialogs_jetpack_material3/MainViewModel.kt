package dev.dacape.example.kotlin.dialogs_jetpack_material3

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import android.app.Application;
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(application: Application): ViewModel() {

    var openDialog by mutableStateOf(false)

    init {

    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OpenDialog -> {
                openDialog = true
            }
            is Event.CloseDialog -> {
                openDialog = false
            }
        }
    }

}


class MainViewModelFactory (private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}