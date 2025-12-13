package com.example.recicleview.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recicleview.databinding.ItemCharBinding
import com.example.recicleview.models.Char

class ViewHolderChar(
    view: View,
    ) : RecyclerView.ViewHolder(view) {

    var binding: ItemCharBinding = ItemCharBinding.bind(view)

    //Metodo que se encarga de mapear los item por propiedad del modelo.
    fun renderize(char: Char,
          deleteOnClick: (Int) -> Unit, // Añadimos los eventos onlick de delete y update en el constructor del renderize
                  // Ojo: si lo añadimos al constructor nos dara fallo en el adapter ya que no sabe que le pasa, sin embargo en el renderice si lo sabe ya que accede a estos datos
          updateOnClick: (Int) -> Unit) {
        binding.txtviewCharName.text = char.name
        binding.txtviewCharType.text = char.type
        binding.txtviewCharOcupation.text = char.ocupation
        binding.txtviewCharAge.text = "Edad: ${char.age} años"

        //como image es un Int (R.drawable), se carga directamente
        binding.imageChar.setImageResource(char.image)

        setupClickListeners(deleteOnClick,updateOnClick)
    }

    /**
     * Saca la lógica de asignación de OnClickListeners del renderize.
     * Recibe las lambdas necesarias como parámetros.
     */
    private fun setupClickListeners(
        deleteAction: (Int) -> Unit, // Renombro las lambdas para mayor claridad
        updateAction: (Int) -> Unit
    ) {
        //Seguimos usando adapterPosition (la posición correcta en el momento del click)
        binding.btnEditChar.setOnClickListener {
            updateAction(adapterPosition)
        }
        binding.btnDeleteChar.setOnClickListener {
            deleteAction(adapterPosition)
        }
    }
}
