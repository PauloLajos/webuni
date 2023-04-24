package hu.webinu.todorecyclerviewdemo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.webinu.todorecyclerviewdemo.data.Todo
import java.text.SimpleDateFormat
import java.util.*

class TodoDialog : DialogFragment() {

    interface TodoHandler{
        fun todoCreated(todo: Todo)
    }

    lateinit var todoHandler: TodoHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TodoHandler){
            todoHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the TodoHandler interface.")
        }
    }

    lateinit var etTodoText: EditText
    lateinit var cbTodoDone: CheckBox


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Todo dialog")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.todo_dialog, null
        )

        etTodoText = dialogView.findViewById(R.id.etTodoText)
        cbTodoDone = dialogView.findViewById(R.id.cbTodoDone)

        dialogBuilder.setView(dialogView)


        dialogBuilder.setPositiveButton("Ok") {
                dialog, which ->
                    todoHandler.todoCreated(
                        Todo(
                            SimpleDateFormat("yyyy.MM.dd.").format(Date(System.currentTimeMillis())),
                            cbTodoDone.isChecked,
                            etTodoText.text.toString()
                    )
            )
        }
        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }


        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        etTodoText.requestFocus()

        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            0
        )
    }
}