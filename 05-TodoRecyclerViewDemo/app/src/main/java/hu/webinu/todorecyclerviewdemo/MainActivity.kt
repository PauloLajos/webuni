package hu.webinu.todorecyclerviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import hu.webinu.todorecyclerviewdemo.adapter.TodoAdapter
import hu.webinu.todorecyclerviewdemo.data.Todo
import hu.webinu.todorecyclerviewdemo.databinding.ActivityMainBinding
import hu.webinu.todorecyclerviewdemo.touch.TodoRecyclerTouchCallback
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity(), TodoDialog.TodoHandler {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val PREF_SETTINGS = "PREF_SETTINGS"
        const val KEY_LAST_OPENED = "KEY_LAST_OPENED"
        const val KEY_FIRST_START = "KEY_FIRST_START"
    }

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

        if (isFirstStart()) {
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.fab)
                .setPrimaryText("Create todo")
                .setSecondaryText("Tap here to create new todo")
                .show()
        }

        saveData()
    }

    private fun saveData() {
        val sp = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(KEY_LAST_OPENED, Date(Calendar.getInstance().timeInMillis).toString())
        editor.putBoolean(KEY_FIRST_START,false)
        editor.apply()
    }

    private fun isFirstStart(): Boolean {
        val sp = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE)
        Toast.makeText(this,sp.getString(KEY_LAST_OPENED, "This is the first time"),Toast.LENGTH_LONG).show()
        return sp.getBoolean(KEY_FIRST_START, true)
    }

    override fun todoCreated(todo: Todo) {
        todoAdapter.addTodo(todo)
    }
}