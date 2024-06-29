package com.example.protecsup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class UsuarioAdapter (context: Context, usuarios: List<Usuario>): ArrayAdapter<Usuario>(context, 0, usuarios){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val usuario = getItem(position)
        var view = convertView
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)

        val name = view?.findViewById<TextView>(R.id.nombre)
        val lastname = view?.findViewById<TextView>(R.id.apellido)
        val email = view?.findViewById<TextView>(R.id.correo)
        val phone = view?.findViewById<TextView>(R.id.telefono)
        val dni = view?.findViewById<TextView>(R.id.dni)



        name?.text = usuario?.nombre
        lastname?.text = usuario?.apellido
        email?.text = usuario?.correo
        phone?.text = usuario?.telefono
        dni?.text = usuario?.dni


        return  view!!
    }
}