package com.example.protecsup

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth = FirebaseAuth.getInstance()

        val signInEmail = findViewById<EditText>(R.id.signInEmail)
        val signInPassword = findViewById<EditText>(R.id.signInPassword)
        signInPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        val signInPasswordLayout = findViewById<TextInputLayout>(R.id.signInPasswordLayout)
        val signInBtn = findViewById<Button>(R.id.signInBtn)
        val signInProgressBar = findViewById<ProgressBar>(R.id.signInProgressbar)
        val signInText = findViewById<TextView>(R.id.signInText)

        signInText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {
            handleSignIn(
                signInEmail,
                signInPassword,
                signInPasswordLayout,
                signInProgressBar
            )
        }
    }

    private fun handleSignIn(
        emailField: EditText,
        passwordField: EditText,
        passwordLayout: TextInputLayout,
        progressBar: ProgressBar
    ) {
        progressBar.visibility = View.VISIBLE

        val email = emailField.text.toString()
        val password = passwordField.text.toString()

        when {
            email.isEmpty() -> {
                emailField.error = "Ingrese su correo válido"
                progressBar.visibility = View.GONE
                showToast("Ingrese datos válidos")
            }
            !email.matches(emailPattern.toRegex()) -> {
                emailField.error = "Ingrese un correo válido"
                progressBar.visibility = View.GONE
                showToast("Ingrese un correo válido")
            }
            password.length < 6 -> {
                passwordField.error = "La contraseña debe tener más de 6 caracteres"
                progressBar.visibility = View.GONE
                showToast("La contraseña debe tener más de 6 caracteres")
            }
            else -> {
                signInWithEmailPassword(email, password, progressBar)
            }
        }
    }

    private fun signInWithEmailPassword(email: String, password: String, progressBar: ProgressBar) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()  // Optional: Finish the current activity so the user can't go back to the sign-in screen
            } else {
                showToast("El correo o contraseña no son válidos")
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}