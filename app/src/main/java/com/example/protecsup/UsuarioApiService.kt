package com.example.protecsup

import retrofit2.http.GET

interface UsuarioApiService {
    @GET("usersapi")
    suspend fun  selectedUsuario():ArrayList<Usuario>
}