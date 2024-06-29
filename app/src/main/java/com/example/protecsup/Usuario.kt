package com.example.protecsup

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("apellido")
    val apellido: String,
    @SerializedName("correo")
    val correo: String,
    @SerializedName("telefono")
    val telefono: String,
    @SerializedName("dni")
    val dni: String,
)
