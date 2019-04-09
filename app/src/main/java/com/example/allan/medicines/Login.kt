package com.example.allan.medicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*

class Login : AppCompatActivity() {
    // instancia de al tabla de firebase para la autenticacion
    private lateinit var autenticacion: FirebaseAuth

    // variables para obtener los datos
    private lateinit var usuariotxt: TextView
    private lateinit var contraseñatxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // instancia de los botones
        var btnEntrar = findViewById<Button>(R.id.btnEntrar)
        btnEntrar.setOnClickListener {
            logueo()
            }
        var btnRegis = findViewById<Button>(R.id.btnRegis)
            btnRegis.setOnClickListener {
                val intent = Intent(this, Registrarse::class.java)
                startActivity(intent)
        }
    }

    // funcion para loguearse
    fun logueo(): Unit {
        // obtenemos los datos
        autenticacion = FirebaseAuth.getInstance()
        usuariotxt = findViewById(R.id.Et1)
        contraseñatxt = findViewById(R.id.Et2)

        // pasamos los datos a string
        var usuario = usuariotxt.text.toString()
        var contraseña = contraseñatxt.text.toString()

        // verificamos que no este vacio
        if(!usuario.isEmpty() && !contraseña.isEmpty())
        {
            // funcion de firebase para ingresar
            autenticacion.signInWithEmailAndPassword(usuario, contraseña).addOnCompleteListener(this){
                    task ->
                // si la tarea es exitosa entrara en la app
                if (task.isSuccessful)
                {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                // si no, pues no
                else
                {
                    Toast.makeText(this, "Usuario Incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else
        {
            Toast.makeText(this, "Llene los espacios en blanco para continuar", Toast.LENGTH_SHORT).show()
        }
    }
}