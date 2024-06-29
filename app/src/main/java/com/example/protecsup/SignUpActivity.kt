package com.example.protecsup

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"

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

        signUpPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        signUpCPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        signUpText.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

        signUpBtn.setOnClickListener {
            handleSignUp(
                signUpName,
                signUpEmail,
                signUpPhone,
                signUpPassword,
                signUpCPassword,
                signUpPasswordLayout,
                signUpCPasswordLayout,
                signUpProgressbar
            )
        }
    }

    private fun handleSignUp(
        nameField: EditText,
        emailField: EditText,
        phoneField: EditText,
        passwordField: EditText,
        cpasswordField: EditText,
        passwordLayout: TextInputLayout,
        cpasswordLayout: TextInputLayout,
        progressBar: ProgressBar
    ) {
        val name = nameField.text.toString()
        val email = emailField.text.toString()
        val phone = phoneField.text.toString()
        val password = passwordField.text.toString()
        val cpassword = cpasswordField.text.toString()

        progressBar.visibility = View.VISIBLE

        var isValid = true

        if (name.isEmpty()) {
            nameField.error = "Ingresa tu nombre"
            isValid = false
        }
        if (email.isEmpty()) {
            emailField.error = "Ingresa tu correo electrónico"
            isValid = false
        }
        if (phone.isEmpty()) {
            phoneField.error = "Ingresa tu número telefónico"
            isValid = false
        }
        if (password.isEmpty()) {
            passwordField.error = "Ingresa tu contraseña"
            isValid = false
        }
        if (cpassword.isEmpty()) {
            cpasswordField.error = "Vuelve a ingresar tu contraseña"
            isValid = false
        }
        if (!email.matches(emailPattern.toRegex())) {
            emailField.error = "Ingresa un correo válido"
            isValid = false
        }
        if (phone.length != 9) {
            phoneField.error = "Ingresa un número válido"
            isValid = false
        }
        if (password.length < 6) {
            passwordField.error = "La contraseña debe tener más de 6 caracteres"
            isValid = false
        }
        if (password != cpassword) {
            cpasswordField.error = "No coinciden las contraseñas, inténtalo de nuevo"
            isValid = false
        }

        if (isValid) {
            signUpUser(name, email, phone, password, progressBar)
        } else {
            progressBar.visibility = View.GONE
            showToast("Ingrese datos válidos")
        }
    }

    private fun signUpUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        progressBar: ProgressBar
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser!!.uid
                val databaseRef = database.reference.child("user").child(userId)
                val user = Users(name, email, phone, userId)

                databaseRef.setValue(user).addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful) {
                        startActivity(Intent(this, SigninActivity::class.java))
                        finish()
                    } else {
                        showToast("Algo estuvo mal, vuelve a intentarlo")
                    }
                }
            } else {
                showToast("Algo estuvo mal, vuelve a intentarlo")
            }
            progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
