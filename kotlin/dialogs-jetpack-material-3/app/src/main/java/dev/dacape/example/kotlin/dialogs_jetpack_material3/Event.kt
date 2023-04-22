package dev.dacape.example.kotlin.dialogs_jetpack_material3

sealed class Event {
    object OpenDialog: Event()
    object CloseDialog: Event()
}