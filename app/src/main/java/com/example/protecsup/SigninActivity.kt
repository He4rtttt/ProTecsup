package com.example.protecsup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SigninActivity : AppCompatActivity() {

    private  lateinit var auth : FirebaseAuth
    //private lateinit var  database : FirebaseDatabase
    private  val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth = FirebaseAuth.getInstance()

        val signInEmail = findViewById<EditText>(R.id.signInEmail)
        val signInPassword = findViewById<EditText>(R.id.signInPassword)
        val signInPasswordLayout = findViewById<TextInputLayout>(R.id.signInPasswordLayout)
        val signInBtn = findViewById<Button>(R.id.signInBtn)
        val signInProgressBar = findViewById<ProgressBar>(R.id.signInProgressbar)


        val signInText = findViewById<TextView>(R.id.signInText)

        signInText.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        signInBtn.setOnClickListener {
            signInProgressBar.visibility = View.VISIBLE
            signInPasswordLayout.isPasswordVisibilityToggleEnabled = true

            val email = signInEmail.text.toString()
            val password = signInPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                if (email.isEmpty()){
                    signInEmail.error = "Ingrese su correo valido"
                    signInPasswordLayout.isPasswordVisibilityToggleEnabled = false
                }
                signInProgressBar.visibility = View.GONE
                Toast.makeText(this, "Ingrese datos validos", Toast.LENGTH_SHORT).show()
            }else if (email.matches(emailPattern.toRegex())){
                signInProgressBar.visibility = View.GONE
                signInEmail.error = "Ingresa un correo valido"
                Toast.makeText(this, "Ingresa un correo valido", Toast.LENGTH_SHORT).show()
            }else if (password.length < 6){
                signInPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signInProgressBar.visibility = View.GONE
                signInPassword.error = "La contraseña debe tener mas de 6 caracteres"
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "El correo o constraseña no son validos", Toast.LENGTH_SHORT).show()
                        signInProgressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}