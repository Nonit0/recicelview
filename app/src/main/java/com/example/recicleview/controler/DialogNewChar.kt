package com.example.recicleview.controller

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.recicleview.R // Necesario para acceder a recursos como R.drawable
import com.example.recicleview.databinding.DialogNewCharBinding
import com.example.recicleview.interfaces.EditCharDialogListener
import com.example.recicleview.models.Char

class DialogNewChar : DialogFragment() {

    // El Binding debe ser lateinit y se inicializa en onCreateDialog
    private lateinit var binding: DialogNewCharBinding
    private lateinit var listener: EditCharDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Aseguramos que la Activity implementa la interfaz
        try {
            // Asumimos que la interfaz EditCharDialogListener incluye el método onCharCreated(Char)
            listener = requireActivity() as EditCharDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (requireActivity().toString() + " debe implementar EditCharDialogListener")
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // CORRECCIÓN 1: Usar ViewBinding para inflar el layout
        binding = DialogNewCharBinding.inflate(LayoutInflater.from(context))
        val viewDialogNewChar = binding.root

        builder.setView(viewDialogNewChar)
            .setTitle("Añadir Nuevo Personaje")

            // CORRECCIÓN 3: NO USAR setPositiveButton aquí, ya que el setOnShowListener lo gestiona.
            // Lo ponemos a 'null' para poder sobreescribir su comportamiento.
            .setPositiveButton("Añadir", null)

            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog?.cancel()
            }

        // CORRECCIÓN 2: El control del botón positivo va en setOnShowListener
        return builder.create().apply {
            setOnShowListener {
                val button = (it as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener {

                    val newChar = recoverDataAndValidate() // La lógica de validación está aquí

                    if (newChar != null) {
                        // Si la validación es correcta, notificar al Controller y cerrar
                        listener.onCharCreated(newChar)
                        dismiss()
                    }
                    // Si newChar es null, el diálogo permanece abierto
                }
            }
        }
    }

    /**
     * CRÍTICO: Recupera datos del Binding, valida y crea el objeto Char.
     */
    private fun recoverDataAndValidate(): Char? {
        // Recuperar datos de los EditText usando el binding
        val name = binding.etName.text.toString()
        val type = binding.etType.text.toString()
        val ocupation = binding.etOccupation.text.toString()
        val ageString = binding.etAge.text.toString()
        // Asumimos que la imagen la pones por defecto si no hay campo específico en el diálogo
        val defaultImageId = R.drawable.esquie_live_reaction

        // 1. Validar campos de texto vacíos
        if (name.isBlank() || type.isBlank() || ocupation.isBlank() || ageString.isBlank()) {
            // Añadir feedback visual
            if (name.isBlank()) binding.etName.error = "Nombre requerido"
            if (ageString.isBlank()) binding.etAge.error = "Edad requerida"
            return null // Fallo en la validación
        }

        // 2. Validar que la edad sea un número (CORRECCIÓN 4: Int.isEmpty() no existe)
        val age = ageString.toIntOrNull()
        if (age == null) {
            binding.etAge.error = "La edad debe ser un número válido."
            return null // Fallo en la validación
        }

        // 3. Si todo es válido, crear el objeto Char (CORRECCIÓN 5: Usar 5 parámetros)
        return Char(
            name = name,
            type = type,
            ocupation = ocupation,
            age = age,
            image = defaultImageId // Usa un ID de recurso válido
        )
    }

    // El método recoverDataLayout ha sido eliminado, ya que no era funcional.
}