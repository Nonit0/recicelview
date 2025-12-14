package com.example.recicleview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recicleview.models.Char
import com.example.recicleview.R

class AdapterChar(
    var listChar: MutableList<Char>,
    val onDeleteChar: (Int)-> Unit, // Implementamos los botones que tendraá cada instancia de CHAR en nuestro recicleView
    val onUpdateChar: (Int, Char)-> Unit // Estos vienen del viewHolder
) : RecyclerView.Adapter<ViewHolderChar>() {

    /*
     Metodo que crea la view del ViewHolderChar
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChar {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemChar = R.layout. item_char //accedo al xml del item a crear.
        val view = layoutInflater.inflate(layoutItemChar, parent, false)
        return ViewHolderChar(view, onDeleteChar, onUpdateChar)
    }

    /*
     Este metodo renderiza todos los datos de cada char
     */
    override fun onBindViewHolder(holder: ViewHolderChar, position: Int) {
        holder.renderize(listChar[position])
    }

    /*
     Devuelve el número de objetos a representar en el RecyclerView
     */
    override fun getItemCount(): Int = listChar.size
}
