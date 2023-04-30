package hu.webuni.roomgradedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.webuni.roomgradedemo.data.AppDatabase
import hu.webuni.roomgradedemo.data.Grade
import hu.webuni.roomgradedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding

    var threadEnable: Boolean = false

    inner class MyThread : Thread() {
        override fun run() {
            while (threadEnable) {
                runOnUiThread {
                    mainBinding.tvData.append("#")
                }
                sleep(1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.btnStart.setOnClickListener {
            if (!threadEnable) {
                threadEnable = true
                MyThread().start()
            } else {
                // if MyThread.run() end, thread stopping
                threadEnable = false
            }
        }

        mainBinding.btnSave.setOnClickListener {
            saveGrade()
        }

        mainBinding.btnQuery.setOnClickListener {
            queryGrade()
        }
    }

    fun saveGrade() {
        Thread {
            AppDatabase.getInstance(this@MainActivity).gradeDao().insertGrades(
                Grade(null,
                    mainBinding.etStudentName.text.toString(),
                    mainBinding.etGrade.text.toString())
            )
        }.start()
    }

    fun queryGrade(){
        Thread{
            var grades = AppDatabase.getInstance(this@MainActivity).gradeDao().getSomeGrades("2")
            runOnUiThread {
                mainBinding.tvResult.text = ""
                grades.forEach {
                    mainBinding.tvResult.append("${it.studentName} - ${it.grade}\n")
                }
            }
        }.start()
    }

    override fun onStop() {
        // if app stopping, also thread stopping
        threadEnable = false
        super.onStop()
    }
}