package com.example.recicleview.controller

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.recicleview.R // Necesario para acceder a recursos
import com.example.recicleview.databinding.DialogNewCharBinding
import com.example.recicleview.interfaces.EditCharDialogListener
import com.example.recicleview.models.Char

class DialogNewChar : DialogFragment() {

    // Constantes y Variables de Modo
    private enum class Mode { NEW, EDIT }
    private var currentMode: Mode = Mode.NEW
    private var charPosition: Int = -1
    private var charToEdit: Char? = null

    // Constantes para el Bundle
    companion object {
        private const val ARG_MODE = "arg_mode"
        private const val ARG_POSITION = "arg_position"
        private const val ARG_CHAR = "arg_char"

        // MODO AÑADIR
        fun newInstance(): DialogNewChar {
            return DialogNewChar()
        }

        // MODO EDITAR
        fun newInstance(position: Int, charToEdit: Char): DialogNewChar {
            val fragment = DialogNewChar()
            val args = Bundle().apply {
                putString(ARG_MODE, Mode.EDIT.name)
                putInt(ARG_POSITION, position)
                // Usamos 'as java.io.Serializable' si Char no está reconocido directamente
                putSerializable(ARG_CHAR, charToEdit as java.io.Serializable)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: DialogNewCharBinding
    private lateinit var listener: EditCharDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Asignación del Listener (el código está correcto)
        try {
            listener = requireActivity() as EditCharDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (requireActivity().toString() + " debe implementar EditCharDialogListener")
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // 1. Lectura de Argumentos y Definición de Modo
        arguments?.let {
            if (it.getString(ARG_MODE) == Mode.EDIT.name) {
                currentMode = Mode.EDIT
                charPosition = it.getInt(ARG_POSITION)
                // Conversión segura de Serializable a Char
                charToEdit = it.getSerializable(ARG_CHAR) as? Char
            }
        }

        // Inicialización de Binding e Inflado
        binding = DialogNewCharBinding.inflate(LayoutInflater.from(context))
        val viewDialogNewChar = binding.root

        // 2. Configuración Dinámica del Builder
        val dialogTitle = if (currentMode == Mode.EDIT) "Editar Personaje" else "Añadir Nuevo Personaje"
        val positiveButtonText = if (currentMode == Mode.EDIT) "Guardar" else "Añadir"

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(viewDialogNewChar)
            .setTitle(dialogTitle)

            // Definimos el botón positivo con NULL y el texto dinámico
            .setPositiveButton(positiveButtonText, null)

            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog?.cancel()
            }

        // 3. Precarga de Datos (Solo en modo EDIT)
        if (currentMode == Mode.EDIT && charToEdit != null) {
            binding.etName.setText(charToEdit!!.name)
            binding.etType.setText(charToEdit!!.type)
            binding.etOccupation.setText(charToEdit!!.ocupation)
            binding.etAge.setText(charToEdit!!.age.toString())
            // Nota: Aquí iría la precarga de la imagen si se usa un ImageView
        }

        // 4. Sobreescritura del Listener del Botón Positivo (Patrón de Validación)
        return builder.create().apply {
            setOnShowListener {
                val button = (it as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener {

                    val newChar = recoverDataAndValidate()

                    if (newChar != null) {
                        // LÓGICA DINÁMICA DE RETORNO
                        if (currentMode == Mode.EDIT) {
                            // MODO EDITAR: Llama a onCharEdited
                            listener.onCharEdited(charPosition, newChar)
                        } else {
                            // MODO AÑADIR: Llama a onCharCreated
                            listener.onCharCreated(newChar)
                        }
                        dismiss() // Cerrar el diálogo solo si la validación es exitosa
                    }
                    // Si newChar es null, el diálogo permanece abierto.
                }
            }
        }
    }

    /**
     * Recupera datos del Binding, valida y crea el objeto Char.
     */
    private fun recoverDataAndValidate(): Char? {
        val name = binding.etName.text.toString()
        val type = binding.etType.text.toString()
        val ocupation = binding.etOccupation.text.toString()
        val ageString = binding.etAge.text.toString()

        // --- Lógica para obtener el ID de imagen ---
        val finalImageId: Int = if (currentMode == Mode.EDIT && charToEdit != null) {
            // Si estamos editando, mantenemos el ID de recurso de la imagen original
            charToEdit!!.image
        } else {
            // Si estamos añadiendo, usamos un ID de recurso por defecto (Asegúrate de que este ID existe)
            R.drawable.esquie_live_reaction
        }

        // 1. Validar campos de texto vacíos
        if (name.isBlank() || type.isBlank() || ocupation.isBlank() || ageString.isBlank()) {
            if (name.isBlank()) binding.etName.error = "Nombre requerido"
            if (ageString.isBlank()) binding.etAge.error = "Edad requerida"
            if (ocupation.isBlank()) binding.etOccupation.error = "Profesión requerrida"
            if (ageString.isBlank()) binding.etAge.error = "Edad requerrida"

            return null
        }

        // 2. Validar que la edad sea un número
        val age = ageString.toIntOrNull()
        if (age == null) {
            binding.etAge.error = "La edad debe ser un número válido."
            return null
        }

        // 3. Crear el objeto Char utilizando el constructor que pide Char(String, String, String, Int, Int)
        return Char(
            name = name,
            type = type,
            ocupation = ocupation,
            age = age,
            image = finalImageId // <-- Ahora es un Int, tal como requiere el constructor.
        )
    }

}