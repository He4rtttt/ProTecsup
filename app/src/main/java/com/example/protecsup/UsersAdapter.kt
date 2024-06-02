package com.example.protecsup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.protecsup.databinding.UserItemBinding

class UsersAdapter(private val context: Context, private val arrayList: ArrayList<Students>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        var convertView = convertView
        convertView = binding.root

        binding.name.text = arrayList[position].name
        binding.email.text = "${arrayList[position].grupo}"
        binding.phone.text = "${arrayList[position].ciclo}"

        return convertView
    }

}
