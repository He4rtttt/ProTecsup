package com.example.protecsup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.protecsup.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var usersAdapter: UsersAdapter
    var studens: ArrayList<Students> = ArrayList()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (auth.currentUser == null) {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }

        studens.add(Students("Pepe", "A","4"))
        studens.add(Students("Jose", "B","4"))
        studens.add(Students("Marcos", "C","4"))

        val adapter = UsersAdapter(this,studens)
        binding.userlist.adapter = adapter

    }


}
