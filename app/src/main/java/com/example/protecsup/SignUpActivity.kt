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

class SignUpActivity : AppCompatActivity() {

    private  lateinit var auth : FirebaseAuth
    private lateinit var  database : FirebaseDatabase
    private  val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val signUpName = findViewById<EditText>(R.id.signUpName)
        val signUpEmail = findViewById<EditText>(R.id.signUpEmail)
        val signUpPhone = findViewById<EditText>(R.id.signUpPhone)
        val signUpPassword = findViewById<EditText>(R.id.signUpPassword)
        val signUpCPassword = findViewById<EditText>(R.id.signUpCPassword)
        val signUpPasswordLayout = findViewById<TextInputLayout>(R.id.signUpPasswordLayout)
        val signUpCPasswordLayout = findViewById<TextInputLayout>(R.id.signUpCPasswordLayout)
        val signUpBtn = findViewById<Button>(R.id.signUpBtn)
        val signUpProgressbar = findViewById<ProgressBar>(R.id.signUpProgressbar)

        val signUpText = findViewById<TextView>(R.id.signUpText)

        signUpText.setOnClickListener {
            val intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
        }
        signUpBtn.setOnClickListener {
            val name = signUpName.text.toString()
            val email = signUpEmail.text.toString()
            val phone = signUpPhone.text.toString()
            val password = signUpPassword.text.toString()
            val cpassword = signUpCPassword.text.toString()

            signUpProgressbar.visibility = View.VISIBLE
            signUpPasswordLayout.isPasswordVisibilityToggleEnabled = true
            signUpCPasswordLayout.isPasswordVisibilityToggleEnabled = true

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || cpassword.isEmpty()){
                if (name.isEmpty()){
                    signUpName.error = "Ingresa tu nombre"
                }
                if (email.isEmpty()){
                    signUpEmail.error = "Ingresa tu correo electronico"
                }
                if (phone.isEmpty()){
                    signUpPhone.error = "Ingresa tu numero telefonico"
                }
                if (password.isEmpty()){
                    signUpPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpPassword.error = "Ingresa tu contraseña"
                }
                if (cpassword.isEmpty()){
                    signUpCPasswordLayout.isPasswordVisibilityToggleEnabled = false
                    signUpCPassword.error = "Vuelve a ingresar tu contraseña"
                }
                Toast.makeText(this, "Ingrese datos validos", Toast.LENGTH_SHORT).show()
                signUpProgressbar.visibility = View.GONE
            }else if (email.matches(emailPattern.toRegex())){
                signUpProgressbar.visibility = View.GONE
                signUpEmail.error = "Ingresa un correo valido"
                Toast.makeText(this, "Ingresa un correo valido", Toast.LENGTH_SHORT).show()
            }else if (phone.length != 9){
                signUpProgressbar.visibility = View.GONE
                signUpPhone.error = "Ingresa un numero valido"
                Toast.makeText(this, "Ingresa un numero valido", Toast.LENGTH_SHORT).show()
            }else if (password.length < 6){
                signUpPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpProgressbar.visibility = View.GONE
                signUpPassword.error = "La contraseña debe tener mas de 6 caracteres"
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show()
            }else if (password != cpassword){
                signUpCPasswordLayout.isPasswordVisibilityToggleEnabled = false
                signUpProgressbar.visibility = View.GONE
                signUpCPassword.error = "No coinciden las contraseñas, intentalo de nuevo"
                Toast.makeText(this, "No coinciden las contraseñas, intentalo de nuevo", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val databaseRef = database.reference.child("user").child(auth.currentUser!!.uid)
                        val users : Users = Users(name, email, phone, auth.currentUser!!.uid)
                        databaseRef.setValue(users).addOnCompleteListener {
                            if (it.isSuccessful){
                                val intent = Intent(this, SigninActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "Algo estubo mal, vuelve a intentarlo", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(this, "Algo estubo mal, vuelve a intentarlo", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}