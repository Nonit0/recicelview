package com.example.recicleview.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recicleview.databinding.ItemCharBinding
import com.example.recicleview.models.Char

class ViewHolderChar(view: View) : RecyclerView.ViewHolder(view) {

    var binding: ItemCharBinding = ItemCharBinding.bind(view)

    //Metodo que se encarga de mapear los item por propiedad del modelo.
    fun renderize(char: Char) {
        binding.txtviewCharName.text = char.name
        binding.txtviewCharType.text = char.type
        binding.txtviewCharOcupation.text = char.ocupation
        binding.txtviewCharAge.text = "Edad: ${char.age} años"

        //como image es un Int (R.drawable), se carga directamente
        binding.imageChar.setImageResource(char.image)
    }
}