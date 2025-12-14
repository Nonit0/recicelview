package com.example.recicleview.models

// Añadir la implementación de java.io.Serializable
class Char(
    var name: String,
    var type: String,
    var ocupation: String,
    var age: Int,
    var image: Int
) : java.io.Serializable { // <--- Se un objeto serializable
// Al agregar : java.io.Serializable a tu clase Char, le estás diciendo al sistema Android (y a Java):
//
//    "Soy un objeto seguro. Puedes descomponerme en un flujo de bytes (bytes stream) y reconstruirme más tarde."
    override fun toString(): String {
        return "Char(name='$name', type='$type', ocupation='$ocupation', age=$age, image='$image')"
    }
}