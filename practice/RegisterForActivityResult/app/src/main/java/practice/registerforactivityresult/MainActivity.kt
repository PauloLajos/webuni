package practice.registerforactivityresult

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import practice.registerforactivityresult.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    private var mGetNameActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Validity checks
        if (RESULT_OK != result.resultCode) {
            mBinding.textViewResultData.text = "Activity has returned cancelled."
            return@registerForActivityResult
        }
        val intent = result.data
        if (intent == null) {
            mBinding.textViewResultData.text = "Activity hasn't returned an intent."
            return@registerForActivityResult
        }
        else if (!intent.hasExtra("nameData")) {
            mBinding.textViewResultData.text = "Activity hasn't returned extra data."
            return@registerForActivityResult
        }
        // Valid result returned
        mBinding.textViewResultData.text = (
            "Activity has returned " + intent.getStringExtra("nameData"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        mBinding.fab.setOnClickListener {
            launchNameActivity()
        }
    }

    private fun launchNameActivity() {
        /***
        val intent = Intent(this, NameActivity::class.java)
        mGetNameActivity.launch(intent)
        */

        // Use the Kotlin extension in activity-ktx
        // passing it the Intent you want to start
        mGetNameActivity.launch(Intent(this, NameActivity::class.java))
    }
}