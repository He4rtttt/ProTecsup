package com.example.protecsup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ExecutorCompat
import androidx.lifecycle.lifecycleScope
import com.example.protecsup.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ThreadPoolExecutor

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    var studens: ArrayList<Students> = ArrayList()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val urlBase = "http://161.132.47.44:8080/"
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnpuerta = findViewById<Button>(R.id.puerta)
        val listUsuario = findViewById<Button>(R.id.btnListUsuarios)
        val listar = findViewById<ListView>(R.id.userlist)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val retrofit = Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(UsuarioApiService::class.java)
        val context: Context = this

        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SigninActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
        btnpuerta.setOnClickListener {
            val intent = Intent(this, PuertaActivity::class.java)
            startActivity(intent)
        }

        if (auth.currentUser == null) {
            val intent = Intent(this, SigninActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        listUsuario.setOnClickListener {
            lifecycleScope.launch {
                try {
                    Log.d("PuertaActivity", "Obteniendo datos de las puertas...")
                    val listaUsuario = service.selectedUsuario()
                    Log.d("PuertaActivity", "Datos obtenidos: ${listaUsuario.size} elementos")

                    val adapter = UsuarioAdapter(context, listaUsuario)
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

    override fun onBackPressed() {
        super.onBackPressed()
    }
}