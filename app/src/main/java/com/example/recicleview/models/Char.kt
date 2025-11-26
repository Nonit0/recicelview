package com.example.recicleview.models

class Char(
    var name: String,
    var type: String,
    var ocupation: String,
    var age: Int,
    var image: Int
) {
    override fun toString(): String {
        return "Char(name='$name', type='$type', ocupation='$ocupation', age=$age, image='$image')"
    }
}
