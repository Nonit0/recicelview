package com.example.recicleview.controller

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recicleview.MainActivity
import com.example.recicleview.adapter.AdapterChar
import com.example.recicleview.dao.DaoChar
import com.example.recicleview.models.Char

class ControlerChar(val context: Context) {
    lateinit var listChar: MutableList<Char> //lista de objetos
    lateinit var adapterChar: AdapterChar // Propiedad para guardar la referencia del Adapter


    init {
        initData()
    }

    fun initData() {
        //llamamos al singleton Daochar
        listChar = DaoChar.myDao.getDataChar().toMutableList()
    }

    fun loggOut() {
        Toast.makeText(context, "He mostrado los datos en pantalla", Toast.LENGTH_LONG).show()
        listChar.forEach {
            println(it)
        }
    }

    // CORRECCIÓN 1: Evitar el crash al borrar
    fun delChar(pos : Int){
        if (pos >= 0 && pos < listChar.size) {
            val charToDelete = listChar[pos]

            Toast.makeText( context, "Borraremos el char ${charToDelete.name}",
                Toast.LENGTH_LONG).show()

            listChar.removeAt(pos)

            // USAR LA NOTIFICACIÓN ROBUSTA PARA EVITAR EL CRASH DE ÍNDICE
            adapterChar.notifyDataSetChanged()

            Toast.makeText(context, "Eliminado: ${charToDelete.name}", Toast.LENGTH_SHORT).show()
        }
    }

    fun addChar(newChar: Char) {
        // 1. Modificar la Fuente de Datos: Añadir el nuevo objeto a la lista
        listChar.add(newChar)

        // 2. Notificar al Adaptador para que la vista se actualice
        // Usamos notifyItemInserted para una animación más eficiente y específica.
        // El índice es el último de la lista (el tamaño actual menos 1).
        val position = listChar.size - 1
        adapterChar.notifyItemInserted(position)

        // Opcional: Mostrar un mensaje al usuario
        Toast.makeText(context, "¡Personaje '${newChar.name}' añadido con éxito!", Toast.LENGTH_SHORT).show()
    }

    // ESTA es la función que pasas al Adapter en setAdapter()
    fun updateChar(pos: Int) {
        if (pos >= 0 && pos < listChar.size) {
            val charToEdit = listChar[pos]

            // Lógica: Abrir el diálogo o Activity de edición.
            // Aquí NO tienes los nuevos datos todavía.
            Toast.makeText(context, "Abriendo edición para: ${charToEdit.name}", Toast.LENGTH_SHORT).show()

            // Aquí se crea y muestra el diálogo.
            // val dialog = EditCharDialogFragment.newInstance(pos, charToEdit)
            // dialog.show(myActivity.supportFragmentManager, "EditCharDialog")

            // Mensaje
            Toast.makeText(context, "Abriendo edición para: ${charToEdit.name}", Toast.LENGTH_SHORT).show()
        }
    }

    // ESTA es la función que se llama cuando el usuario pulsa "Guardar" en el diálogo.
    // Lo que recibes del diálogo (el objeto Char ya modificado por el usuario)
    fun applyUpdate(pos: Int, modifiedChar: Char) {
        if (pos >= 0 && pos < listChar.size) {

            // Asumiendo que listChar es una lista de objetos de datos (data class)

            // 1. Reemplazas el objeto viejo de la lista con el objeto modificado
            listChar[pos] = modifiedChar

            // 2. Notificar al Adapter que SOLO esta posición cambió
            adapterChar.notifyItemChanged(pos)

            // 3. Lógica de Base de Datos:
            // Usas el ID existente en modifiedChar.id para UPDATE
            //Si hubiera una base de datos, por ejemplo
            // DaoChar.myDao.updateChar(modifiedChar)

            Toast.makeText(context, "Personaje actualizado: ${modifiedChar.name}", Toast.LENGTH_SHORT).show()
        }
    }

    fun setAdapter() {
        val myActivity = context as MainActivity

        // CORRECCIÓN 3: Configurar el Layout Manager
        myActivity.binding.recyclerView.layoutManager =
            LinearLayoutManager(myActivity, LinearLayoutManager.HORIZONTAL, false)

        // CORRECCIÓN 2: Asignar la instancia a la propiedad lateinit
        adapterChar = AdapterChar(
            listChar,
            onDeleteChar = { pos -> delChar(pos) },
            onUpdateChar = { pos -> updateChar(pos) }
        )

        // Asignar el adapter al RecyclerView
        myActivity.binding.recyclerView.adapter = adapterChar
    }

}
