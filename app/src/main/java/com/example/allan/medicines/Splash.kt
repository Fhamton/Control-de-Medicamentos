package com.example.allan.medicines

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash : AppCompatActivity() {

    // Variable que detecta si el splash esta corriendo.
    var elhandler : Handler? = null

    // variable que controla el tiempo que el splash se proyectara.
    val tiempo : Long = 3100

    // Variable que controla si el layout funciona.
    val corriendo: Runnable = Runnable()
    {
        if (!isFinishing)
        {
            // Cuando termina de correr abre el main
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Instancia del Handler
        elhandler = Handler()
        elhandler!!.postDelayed(corriendo, tiempo)
    }
    override fun onDestroy()
    {
        if (true)
        {
            elhandler!!.removeCallbacks(corriendo)
        }
        super.onDestroy()
    }
}
