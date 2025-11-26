package com.example.recicleview.dao

import com.example.recicleview.interfaces.InterfaceDao
import com.example.recicleview.models.Char
import com.example.recicleview.objects_models.Repository

class DaoChar private constructor() : InterfaceDao {

    companion object {
        //Singleton: se crea solo una vez (la primera vez que se llama al constructor) y se reutiliza
        val myDao: DaoChar by lazy {
            DaoChar()
        }
    }

    //Metodo que accede al repositorio y devuelve todos los char
    override fun getDataChar(): List<Char> = Repository.listChar
}
