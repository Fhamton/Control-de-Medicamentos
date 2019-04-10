package com.example.allan.medicines

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.ULocale
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.muddzdev.styleabletoastlibrary.StyleableToast
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Calendar.*
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {

    // variabels que obtienen datos
    lateinit var medicina : EditText
    lateinit var clase : EditText
    lateinit var aplicacion : EditText
    lateinit var vencimiento: EditText
    lateinit var precio : EditText
    lateinit var dia: EditText
    lateinit var mes: EditText
    lateinit var año: EditText

    // variables que obtienen y comparan fechas
    var calendar = GregorianCalendar(Locale.getDefault())
    var diaSys = calendar.get(Calendar.DAY_OF_MONTH)
    var mesSys = calendar.get(Calendar.MONTH) + 1
    var añoSys = calendar.get(Calendar.YEAR)

    // variables de botones
    lateinit var btnguardar : Button
    lateinit var inventario : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // llenamos los datos
        medicina = findViewById(R.id.nombre)
        clase = findViewById(R.id.clasificacion)
        aplicacion = findViewById(R.id.aplicacion)
        vencimiento = findViewById(R.id.dia)
        precio = findViewById(R.id.precio)

        dia = findViewById(R.id.dia)
        mes = findViewById(R.id.mes)
        año = findViewById(R.id.año)

        btnguardar   = findViewById(R.id.btn1)
        inventario   = findViewById(R.id.btn2)

        // eventos en los botones
        btnguardar.setOnClickListener {

            // variables que controlan la fecha de vencimiento
            var dia2 = dia.getText().toString()
            var mes2 = mes.getText().toString()
            var año2 = año.getText().toString()

            // comparamos las fechas
            var dia3 :Int= Integer.parseInt(dia2)
            var mes3 :Int= Integer.parseInt(mes2)
            var año3 :Int= Integer.parseInt(año2)

            // Fechas
            var primeraFecha = LocalDate.of(añoSys,mesSys,diaSys)
            var segundaFecha = LocalDate.of(año3, mes3, dia3)
            var dias = ChronoUnit.DAYS.between(primeraFecha, segundaFecha).toInt()

            // si los dias son 0 o menores, el producto no sera agregado
            if(dias==0){
                StyleableToast.makeText(this,"Este producto vence hoy", R.style.exampleToast, Toast.LENGTH_LONG).show()

            }
            else if (dias < 0)
            {
                Toast.makeText(this,"Este producto ya venció",Toast.LENGTH_LONG).show()
            }
            else
            {
                guardar()
            }
}
        inventario.setOnClickListener {
            val intent = Intent(this, Mostrar::class.java)
            startActivity(intent)
        }
    }

    // funcion para guardar datos
    private fun guardar(){

        dia = findViewById(R.id.dia)
        mes = findViewById(R.id.mes)
        año = findViewById(R.id.año)

        var dia2 = dia.getText().toString()
        var mes2 = mes.getText().toString()
        var año2 = año.getText().toString()

        val nombre    = medicina.text.toString().trim()
        val clas    = clase.text.toString().trim()
        val ap      = aplicacion.text.toString().trim()
        val fecha   =  ("" + dia2 + "/" + mes2 + "/ " + año2).trim()
        val pre   = precio.text.toString().trim()
        val day   = dia.text.toString().trim()
        val mnth   = mes.text.toString().trim()
        val yir   = año.text.toString().trim()

        if (nombre.isEmpty()){
            medicina.error = "Ingrese el dato de forma correcta"
            return
        }
        if (clas.isEmpty()){
            clase.error = "Ingrese el dato de forma correcta"
            return
        }
        if (ap.isEmpty()){
            aplicacion.error = "Ingrese el dato de forma correcta"
            return
        }
        if (pre.isEmpty()){
            precio.error = "Ingrese el dato de forma correcta"
            return
        }
        if (day.isEmpty()){
            precio.error = "Ingrese el dato de forma correcta"
            return
        }
        if (mnth.isEmpty()){
            precio.error = "Ingrese el dato de forma correcta"
            return
        }
        if (yir.isEmpty()){
            precio.error = "Ingrese el dato de forma correcta"
            return
        }
        val BDD = FirebaseDatabase.getInstance().getReference("Medicinas")
        val Id = BDD.push().key
        val medicinas = Medicine(Id!!,nombre,clas,ap,fecha,pre)

        if (Id != null) {
            BDD.child(Id).setValue(medicinas).addOnCompleteListener{
                Toast.makeText(this,"Guardado Exitosamente",Toast.LENGTH_LONG).show()
            }
        }
    }
}

