package com.example.recicleview.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recicleview.databinding.ItemCharBinding
import com.example.recicleview.models.Char

class ViewHolderChar(
    view: View,
    var onDeleteChar: (Int) -> Unit,
    var onUpdateChar: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(view) {

    var binding: ItemCharBinding = ItemCharBinding.bind(view)

    //Metodo que se encarga de mapear los item por propiedad del modelo.
    fun renderize(char: Char){
        binding.txtviewCharName.text = char.name
        binding.txtviewCharType.text = char.type
        binding.txtviewCharOcupation.text = char.ocupation
        binding.txtviewCharAge.text = "Edad: ${char.age} años"

        //como image es un Int (R.drawable), se carga directamente
        binding.imageChar.setImageResource(char.image)
        setOnClickListeners()
    }

    /**
     * Saca la lógica de asignación de OnClickListeners del renderize.
     * Recibe las lambdas necesarias como parámetros.
     */
    private fun setOnClickListeners() {
        binding.btnEditChar.setOnClickListener {
            // Obtiene la posición exacta con bindingAdapterPosition.
            val position = bindingAdapterPosition
            onUpdateChar(position)
        }
        binding.btnDeleteChar.setOnClickListener {
            val position = bindingAdapterPosition
            onDeleteChar(position)
        }
    }
}
