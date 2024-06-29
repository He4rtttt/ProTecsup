package com.example.protecsup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PuertaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val urlBase = "http://161.132.47.44:8080/"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puerta)

        // Configurar OkHttpClient con tiempos de espera aumentados
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS) // Tiempo de espera de conexión
            .readTimeout(30, TimeUnit.SECONDS)    // Tiempo de espera de lectura
            .build()

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(DoorApiService::class.java)
        val context: Context = this
        val btndoor = findViewById<Button>(R.id.btnListDoor)
        val listar = findViewById<ListView>(R.id.lvDoor)

        btndoor.setOnClickListener {
            lifecycleScope.launch {
                try {
                    Log.d("PuertaActivity", "Obteniendo datos de las puertas...")
                    val listapuerta = service.selectDoor()
                    Log.d("PuertaActivity", "Datos obtenidos: ${listapuerta.size} elementos")

                    val adapter = DoorsAdapter(context, listapuerta)
                    listar.adapter = adapter
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("PuertaActivity", "Error al obtener los datos de las puertas", e)
                    runOnUiThread {
                        Toast.makeText(context, "Error al obtener los datos de las puertas", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
