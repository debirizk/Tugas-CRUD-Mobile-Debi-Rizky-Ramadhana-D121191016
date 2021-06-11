package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ListItemBinding
import com.example.todolist.db.ToDo

class MyRecyclerViewAdapter(private val clickListener: (ToDo) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val todoList = ArrayList<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(todoList[position], clickListener)
    }

    fun setList(todo: List<ToDo>) {
        todoList.clear()
        todoList.addAll(todo)

    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(todo: ToDo, clickListener: (ToDo) -> Unit) {
        binding.nameTextView.text = todo.name
        binding.emailTextView.text = todo.time
        binding.listItemLayout.setOnClickListener {
            clickListener(todo)
        }
    }
}