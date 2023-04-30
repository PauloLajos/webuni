package hu.webuni.threaddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.webuni.threaddemo.databinding.ActivityMainBinding

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
    }

    override fun onStop() {
        // if app stopping, also thread stopping
        threadEnable = false
        super.onStop()
    }
}