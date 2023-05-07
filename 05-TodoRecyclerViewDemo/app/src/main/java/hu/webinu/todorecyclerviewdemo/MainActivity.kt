package hu.webinu.todorecyclerviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import hu.webinu.todorecyclerviewdemo.adapter.TodoAdapter
import hu.webinu.todorecyclerviewdemo.data.Todo
import hu.webinu.todorecyclerviewdemo.databinding.ActivityMainBinding
import hu.webinu.todorecyclerviewdemo.touch.TodoRecyclerTouchCallback
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity(), TodoDialog.TodoHandler {

    private lateinit var binding: ActivityMainBinding

    lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title

        todoAdapter = TodoAdapter(this@MainActivity)
        binding.recyclerTodo.adapter = todoAdapter
        //binding.recyclerTodo.layoutManager = GridLayoutManager(this, 2)

        val touchCallback = TodoRecyclerTouchCallback(todoAdapter)
        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerTodo)

        binding.fab.setOnClickListener {
            TodoDialog().show(supportFragmentManager, "Dialog")
        }

        val lastOpened = getDate()
        Toast.makeText(this,lastOpened?:"",Toast.LENGTH_LONG).show()

        saveData()
    }

    private fun saveData() {
        val sp = getSharedPreferences("PREF_SETTINGS", MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("KEY_LAST_OPENED", Date(Calendar.getInstance().timeInMillis).toString())
        editor.apply()
    }

    private fun getDate(): String? {
        val sp = getSharedPreferences("PREF_SETTINGS", MODE_PRIVATE)
        return sp.getString("KEY_LAST_OPENED", "This is the first time")
    }
    override fun todoCreated(todo: Todo) {
        todoAdapter.addTodo(todo)
    }
}