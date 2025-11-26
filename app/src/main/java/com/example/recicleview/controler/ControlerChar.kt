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

    fun setAdapter() {
        val myActivity = context as MainActivity

        // El layout manager es imprescindible
        myActivity.binding.recyclerView.layoutManager =
            LinearLayoutManager(myActivity, LinearLayoutManager.HORIZONTAL, false)

        myActivity.binding.recyclerView.adapter =
            AdapterChar(listChar)
    }


}
