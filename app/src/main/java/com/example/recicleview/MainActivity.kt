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

    /**
     * Llamado por DialogNewChar cuando el usuario pulsa "Guardar" en modo edición.
     * Delega la acción de actualización al ControlerChar.
     */
    override fun onCharEdited(position: Int, modifiedChar: Char) {
        controller.applyUpdate(position, modifiedChar)
    }

    /**
     * Crea y muestra el DialogNewChar en modo AÑADIR.
     */
    fun showNewCharDialog() {
        val dialog = DialogNewChar()
        dialog.show(supportFragmentManager, "NewCharDialog")
    }

    /**
     * Crea y muestra el DialogNewChar en modo EDICIÓN.
     * Este es el puente que usa el ControlerChar cuando se pulsa el botón de editar.
     */
    fun showEditDialog(position: Int, charToEdit: Char) {
        // Usamos el newInstance(position, char) para iniciar en modo EDIT
        val dialog = DialogNewChar.newInstance(position, charToEdit)
        dialog.show(supportFragmentManager, "EditCharDialog")
    }

}
