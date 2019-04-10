package com.example.allan.medicines

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
// clase que adapta los layout y la informacion en los template
class Adaptador(val contexto : Context, val layoutId:Int, val listaMedicinas:List<Medicine>)
    : ArrayAdapter<Medicine>(contexto,layoutId,listaMedicinas){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // infla el layout en el contexto en el que se darrolle la aplicacion
        val layoutInflater : LayoutInflater = LayoutInflater.from(contexto)
        // Obtiene la vista que vamos a representar en el contexto
        val view: View = layoutInflater.inflate(layoutId,null)

        // obtenemos los textviews con la informacion que ingresaremos
        val nombre = view.findViewById<TextView>(R.id.tvnombre)
        val clas = view.findViewById<TextView>(R.id.tvClass)
        val ap = view.findViewById<TextView>(R.id.tvApli)
        val fecha = view.findViewById<TextView>(R.id.tvVen)
        val pre = view.findViewById<TextView>(R.id.tvPrecio)

        // Instancia de los botones
        val actualizar = view.findViewById<TextView>(R.id.btnAct)
        val borrar = view.findViewById<TextView>(R.id.btnElim)

        // Lista que va a ser llenada con los datos
        val medicina = listaMedicinas[position]

        // llenamos los textviews
        nombre.text = medicina.nonbre
        clas.text = medicina.clas
        ap.text = medicina.ap
        fecha.text = medicina.fecha
        pre.text = medicina.pre

        // evento que se dispara al darle click al boton
        actualizar.setOnClickListener {
            actualizarInfo(medicina)
        }

        // evento que se dispara al darle click al boton
        borrar.setOnClickListener {

            // cre un dialogo de alerta en el contexto
            val builder = AlertDialog.Builder(contexto)

            // titulo del dialogo
            builder.setTitle("")

            // mensaje del dialogo
            builder.setMessage("¿Desea eliminar el medicamento?")

            // evento en el boton si
            builder.setPositiveButton("Si"){dialog, which ->
                Toast.makeText(contexto,"Eliminando medicamento.",Toast.LENGTH_SHORT).show()
                // funcion que controla el eliminado de un medicamento
                borrarInfo(medicina)
            }

            // evento del boton cancelar
            builder.setNeutralButton("Cancelar"){_,_ ->
            }

            // creamos el dialogo
            val dialog: AlertDialog = builder.create()
            // mostramos el dialogo
            dialog.show()
        }
        // retornamos la vista
        return view
    }

    private  fun actualizarInfo(medicina:Medicine){

        // se crea un dialogo
        val builder = AlertDialog.Builder(contexto)
        // titulo del dialogo
        builder.setTitle("Actualizar")

        // variables que controlan la vista que vamos a llenar
        val inflater = LayoutInflater.from(contexto)
        val view = inflater.inflate(R.layout.activity_actualizar,null)

        // obtenemos los campos a llenar
        val nombre = view.findViewById<TextView>(R.id.firstnameUpdate)
        val clas = view.findViewById<TextView>(R.id.firstnameUpdate2)
        val ap = view.findViewById<TextView>(R.id.firstnameUpdate3)
        val pre = view.findViewById<TextView>(R.id.firstnameUpdate5)
        val fecha = medicina.fecha

        // asignamos los textos
        nombre.setText(medicina.nonbre)
        clas.setText(medicina.clas)
        ap.setText(medicina.ap)
        pre.setText(medicina.pre)

        // lanzamos la vista
        builder.setView(view)

        // evento del boton si
        builder.setPositiveButton("Actualizado",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                // instancia de la base de datos
                val  BDD = FirebaseDatabase.getInstance().getReference("Medicinas")

                // obtenemos el string de los campos que ingresamos
                val nonb    = nombre.text.toString().trim()
                val cla     = clas.text.toString().trim()
                val apl     = ap.text.toString().trim()
                val prec   = pre.text.toString().trim()

                // verificamos que no esten vacios
                if (nonb.isEmpty()){
                    nombre.error = "Please enter your firstname"
                    return
                }
                if (cla.isEmpty()){
                    clas.error = "Please enter your lastname"
                    return
                }
                if (apl.isEmpty()){
                    ap.error = "Please enter your address"
                    return
                }
                if (prec.isEmpty()){
                    pre.error = "Please enter your department"
                    return
                }

                // Instanciams la clase medicinas para traer los datos
                val medicina = Medicine(medicina.id,nonb,cla,apl,fecha,prec)
                // actuaizamos segun la llave primaria
                BDD.child(medicina.id).setValue(medicina)
                Toast.makeText(contexto,"Actualizado", Toast.LENGTH_LONG).show()
            }})

        // evento del boton cancelar
        builder.setNegativeButton("cancelar",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }
        })

        // mostramos la vista
        val alert = builder.create()
        alert.show()
    }
    // funcion que borra los datos
    private fun borrarInfo(medicina:Medicine){
        val BDD = FirebaseDatabase.getInstance().getReference("Medicinas")
        BDD.child(medicina.id).removeValue()
        Toast.makeText(contexto,"Medicamento eliminado", Toast.LENGTH_LONG).show()
    }
}