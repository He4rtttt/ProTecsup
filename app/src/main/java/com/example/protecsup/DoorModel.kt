package com.example.protecsup

import com.google.gson.annotations.SerializedName

data class DoorModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("codigo")
    val codigo: String,
    @SerializedName("ubicacion")
    val ubicacion: String,
    @SerializedName("estado_actual")
    val estado_actual: String
)
