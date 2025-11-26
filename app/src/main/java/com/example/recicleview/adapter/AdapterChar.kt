package com.example.recicleview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recicleview.models.Char
import com.example.recicleview.R

class AdapterChar(var listChar: MutableList<Char>) :
    RecyclerView.Adapter<ViewHolderChar>() {

    /*
     Metodo que crea la view del ViewHolderChar
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChar {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_char, parent, false)
        return ViewHolderChar(view)
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
