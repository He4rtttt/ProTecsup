package com.example.protecsup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class DoorsAdapter (context: Context, doors:List<DoorModel>): ArrayAdapter<DoorModel>(context,0, doors){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val door = getItem(position)
        var view = convertView
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.door_item, parent,false)

        val id = view?.findViewById<TextView>(R.id.id)
        val salon = view?.findViewById<TextView>(R.id.salon)
        val pabellon = view?.findViewById<TextView>(R.id.pabellon)
        val estado = view?.findViewById<TextView>(R.id.estado)

        id?.text = door?.id.toString()
        salon?.text = door?.codigo
        pabellon?.text = door?.ubicacion
        estado?.text = door?.estado_actual

        return view!!

    }
}