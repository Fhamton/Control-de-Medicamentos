package com.example.allan.medicines

// clase constructora que enlazara los datos de la aplicacion con la base de datos de Firebase
class Medicine(val id:String, val nonbre:String, val clas:String,
               val ap:String, val fecha:String, val pre:String){
    constructor():this( "","","","","",""){}
}