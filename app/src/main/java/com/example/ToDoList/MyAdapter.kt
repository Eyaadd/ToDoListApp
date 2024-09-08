package com.example.ToDoList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ToDoList.databinding.ActivityMainBinding

class MyAdapter(private val itemList:MutableList<UserData>,private val deleteAction: (UserData) -> Unit):RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        lateinit var itemList: List<UserData>
        lateinit var myAdapter: MyAdapter
        lateinit var binding: ActivityMainBinding
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.textView1.text = item.title
        holder.textView2.text = item.description
        holder.textview3.text = item.date
        holder.deleteIcon.setOnClickListener {
            deleteAction(item)
        }
    }

    override fun getItemCount() = itemList.size

    fun removeItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }
}