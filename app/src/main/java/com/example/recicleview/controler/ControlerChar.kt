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

    fun delChar(pos : Int){
        //Aquí habrá que crear un diáglogo para borrar el hotel
        Toast.makeText( context, "Borraremos el hotel de posición $pos",
            Toast.LENGTH_LONG).show()
        listChar.removeAt(pos)
        adapterChar.notifyItemRemoved(pos)
        //Notificamos sólo a esa posición
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

        // El layout manager es imprescindible
        myActivity.binding.recyclerView.layoutManager =
            LinearLayoutManager(myActivity, LinearLayoutManager.HORIZONTAL, false)

        // Instanciamos el Adapter pasándole las lambdas.
        // La lambda captura la posición (pos) y llama a la función del Controller (this.delChar(pos)).
        myActivity.binding.recyclerView.adapter = AdapterChar(
            listChar,
            // Lambda para borrar: Cuando el Adapter pida borrar en 'pos', llamamos a delChar
            onDeleteChar = { pos -> delChar(pos) },
            // Lambda para actualizar: Cuando el Adapter pida actualizar en 'pos', llamamos a updateChar
            onUpdateChar = { pos -> updateChar(pos) }
        )
    }

}
