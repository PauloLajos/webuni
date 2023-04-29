package hu.webinu.todorecyclerviewdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.webinu.todorecyclerviewdemo.R
import hu.webinu.todorecyclerviewdemo.data.Todo
import hu.webinu.todorecyclerviewdemo.touch.TodoTouchHelperCallback
import java.util.*


class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>, TodoTouchHelperCallback {

    //private lateinit var binding: TodoRowBinding

    var todoItems = mutableListOf(
        Todo("2023.04.24.", false, "Go to cinema"),
        Todo("2023.04.22.", true, "Go to laundry"),
        Todo("2023.04.24.", false, "Go to cinema"),
        Todo("2023.04.22.", true, "Go to laundry"),
        Todo("2023.04.24.", false, "Go to cinema"),
        Todo("2023.04.22.", true, "Go to laundry"),
        Todo("2023.04.24.", false, "Go to cinema"),
        Todo("2023.04.22.", true, "Go to laundry"),
        Todo("2023.04.24.", false, "Go to cinema"),
        Todo("2023.04.22.", true, "Go to laundry"),
        Todo("2023.04.24.", false, "Go to cinema"),
        Todo("2023.04.22.", true, "Go to laundry"),
        Todo("2023.04.23.", false, "Go to garage")
    )

    val context: Context

    constructor(context: Context) : super() {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val todoView = LayoutInflater.from(context).inflate(R.layout.todo_row, parent, false)
        return ViewHolder(todoView)
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoItem = todoItems[position]
        holder.tvDate.text = todoItem.createDate
        holder.cbDone.isChecked = todoItem.done
        holder.cbDone.text = todoItem.todoText
        holder.btnDel.setOnClickListener { deleteTodo(holder.adapterPosition) }
    }

    fun addTodo(todo: Todo) {
        todoItems.add(todo)
        notifyItemInserted(todoItems.lastIndex)
    }

    fun deleteTodo(position: Int) {
        todoItems.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var cbDone: CheckBox = itemView.findViewById(R.id.cbDone)
        var btnDel: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onDismissed(position: Int) {
        deleteTodo(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(todoItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }
}