package com.example.recicleview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.recicleview.controller.ControlerChar
import com.example.recicleview.controller.DialogNewChar
import com.example.recicleview.databinding.ActivityMainBinding
import com.example.recicleview.interfaces.EditCharDialogListener
import com.example.recicleview.models.Char // Alias del modelo cahr

class MainActivity : AppCompatActivity(), EditCharDialogListener {

    lateinit var binding: ActivityMainBinding
    lateinit var controller: ControlerChar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Crear el controlador
        controller = ControlerChar(this)

        // Cargar el adapter
        controller.setAdapter()

        binding.fabAddChar.setOnClickListener {
            showNewCharDialog()
        }

    }

    /**
     * Llamado por DialogNewChar cuando el usuario pulsa "Añadir".
     * Delega la acción de inserción al ControlerChar.
     */
    override fun onCharCreated(newChar: Char) {
        controller.addChar(newChar)
    }

    // El método onCharEdited también debe estar aquí:
    override fun onCharEdited(position: Int, modifiedChar: Char) {
        controller.applyUpdate(position, modifiedChar)
    }

    /**
     * Crea y muestra el DialogNewChar.
     */
    fun showNewCharDialog() {
        val dialog = DialogNewChar()
        dialog.show(supportFragmentManager, "NewCharDialog")
    }

}
