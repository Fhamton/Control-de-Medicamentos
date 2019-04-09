package com.example.allan.medicines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.google.firebase.database.*

class Mostrar : AppCompatActivity() {

    // variable que obtiene la referencia de la base de datos
    lateinit var BDD : DatabaseReference

    // lista que controlara los medicamentos
    lateinit var listaMedicinas:MutableList<Medicine>
    lateinit var listview: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.template)

        // instanciamos la lista
        listaMedicinas = mutableListOf()
        listview = findViewById(R.id.lv1)
        BDD = FirebaseDatabase.getInstance().getReference("Medicinas")

        // evaluamos que la base de datos exista
        BDD.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError)
            {

            }
            override fun onDataChange(p0: DataSnapshot)
            {
                if (p0!!.exists())
                {
                    // si existe, añadiremos a la lista todos los datos que se vayan ingresando
                    listaMedicinas.clear()
                    for (e in p0.children){
                        val medicina = e.getValue(Medicine::class.java)
                        listaMedicinas.add(medicina!!)
                    }
                }
                // instanciamos el adaptador con el layout del template
                val adaptador = Adaptador(this@Mostrar,R.layout.activity_medicine, listaMedicinas)
                listview.adapter = adaptador
            }
        })
    }
}