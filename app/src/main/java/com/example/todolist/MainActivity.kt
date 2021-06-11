package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.db.ToDo
import com.example.todolist.db.ToDoDatabase
import com.example.todolist.db.ToDoRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoViewModel: ToDoViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = ToDoDatabase.getInstance(application).ToDoDAO
        val repository = ToDoRepository(dao)
        val factory = ToDoViewModelFactory(repository)
        todoViewModel = ViewModelProvider(this, factory).get(ToDoViewModel::class.java)
        binding.myViewModel = todoViewModel
        binding.lifecycleOwner = this

        todoViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        //
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.todoRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({ selectedItem: ToDo -> listItemClicked(selectedItem) })
        binding.todoRecyclerView.adapter = adapter
        displayToDoList()
    }

    private fun displayToDoList() {
        todoViewModel.getSavedToDo().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(todo: ToDo) {
        todoViewModel.initUpdateAndDelete(todo)
    }
}