package hu.webinu.todorecyclerviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.webinu.todorecyclerviewdemo.adapter.TodoAdapter
import hu.webinu.todorecyclerviewdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        todoAdapter = TodoAdapter(this@MainActivity)
        binding.recyclerTodo.adapter = todoAdapter
    }
}