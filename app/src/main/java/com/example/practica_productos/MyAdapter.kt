package com.example.practica_productos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val itemList : ArrayList<Product>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_card,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = itemList[position]

        holder.nombre.text = currentitem.nombre
        holder.talla.text = currentitem.talla
        holder.color.text = currentitem.color
        holder.precio.text = currentitem.precio.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nombre : TextView = itemView.findViewById(R.id.textView_Nombre)
        val talla : TextView = itemView.findViewById(R.id.textView_Talla)
        val color : TextView = itemView.findViewById(R.id.textView_Color)
        val precio : TextView = itemView.findViewById(R.id.textView_Precio)
    }

}