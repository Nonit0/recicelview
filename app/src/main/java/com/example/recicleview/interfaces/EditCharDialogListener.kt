package com.example.recicleview.interfaces

// En EditCharDialogListener.kt
import com.example.recicleview.models.Char

interface EditCharDialogListener {
    fun onCharEdited(position: Int, modifiedChar: Char)
    fun onCharCreated(newChar: Char) // <--- ESTE MÉTODO ES NECESARIO PARA AÑADIR
}