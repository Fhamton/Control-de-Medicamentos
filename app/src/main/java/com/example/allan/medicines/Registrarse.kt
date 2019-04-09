package com.example.allan.medicines


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser




class Registrarse : AppCompatActivity() {

    val autenticacion = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        var btnEntrar = findViewById<Button>(R.id.btnRegis)
        btnEntrar.setOnClickListener {
            registrar()
        }
    }
    fun registrar(): Unit {

        val usuariotxt = findViewById(R.id.editText2) as EditText
        val contraseñatxt = findViewById(R.id.editText3) as EditText
        val confircontraseñatxt = findViewById(R.id.editText4) as EditText

        var usuario = usuariotxt.text.toString()
        var contraseña = contraseñatxt.text.toString()
        var confirm = confircontraseñatxt.text.toString()

        if(!usuario.isEmpty() && !contraseña.isEmpty() && !confirm.isEmpty() && confirm == contraseña)
        {
            autenticacion.createUserWithEmailAndPassword(usuario, contraseña).addOnCompleteListener(this, OnCompleteListener { task ->
                 if (task.isSuccessful)
                {
                    val usuar = autenticacion.currentUser
                    Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else
        {
            Toast.makeText(this, "Llene los espacios en blanco para continuar", Toast.LENGTH_SHORT).show()
        }
    }
}
