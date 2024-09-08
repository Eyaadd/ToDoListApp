package com.example.ToDoList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val textView1 : TextView = itemView.findViewById(R.id.text1)
    val textView2 : TextView = itemView.findViewById(R.id.text2)
    val textview3 : TextView = itemView.findViewById(R.id.text3)
    val deleteIcon: ImageView = itemView.findViewById(R.id.deleteImg)
}