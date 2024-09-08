package com.example.ToDoList

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ToDoList.databinding.ActivityMainBinding
import com.example.ToDoList.utils.setupDialog
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    var itemList: MutableList<UserData> = mutableListOf()
    private lateinit var userDataDatabase: UserDataDatabase
    private val addTaskDialog: Dialog by lazy {
        Dialog(this).apply {
            setupDialog(R.layout.dialog_fragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        userDataDatabase = UserDataDatabase.getDatabase(this)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)


        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mainBinding.recyclerView.adapter = MyAdapter(itemList) { userData ->
            deleteUser(userData)
        }
        updateRecyclerView()
        val closeImgBtn = addTaskDialog.findViewById<ImageView>(R.id.closeImg)
        closeImgBtn.setOnClickListener {
            addTaskDialog.dismiss()
        }
        mainBinding.add.setOnClickListener {
            addTaskDialog.show()
        }
        addTaskDialog.setOnShowListener {
            val saveBtn = addTaskDialog.findViewById<Button>(R.id.save)
            val titleEditText = addTaskDialog.findViewById<EditText>(R.id.titleT)
            val descriptionEditText = addTaskDialog.findViewById<EditText>(R.id.ToDoT)
            val dateEditText = addTaskDialog.findViewById<EditText>(R.id.DateT)

            saveBtn?.setOnClickListener {
                val title = titleEditText?.text.toString()
                val description = descriptionEditText?.text.toString()
                val date = dateEditText?.text.toString()

                addDataInList(title, description, date)
                addTaskDialog.dismiss()
            }

        }
    }

    fun addDataInList(title:String, description:String, date:String) {
        val userData = UserData(title = title, description = description, date = date)
        lifecycleScope.launch {
            userDataDatabase.userDataDao().insertUserData(userData)
            updateRecyclerView()
        }
    }

    fun updateRecyclerView() {
        lifecycleScope.launch {
            val dataFromRoom = userDataDatabase.userDataDao().getAllUserData()
            itemList.clear()
            itemList.addAll(dataFromRoom)
            mainBinding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

     fun deleteUser(userData: UserData) {
        lifecycleScope.launch {
            userDataDatabase.userDataDao().deleteUserData(userData)
            val position = itemList.indexOf(userData)
            if (position != -1) {
                (mainBinding.recyclerView.adapter as MyAdapter).removeItem(position)
            }
        }
    }
}