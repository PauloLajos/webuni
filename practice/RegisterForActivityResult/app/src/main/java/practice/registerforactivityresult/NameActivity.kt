package practice.registerforactivityresult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import practice.registerforactivityresult.databinding.ActivityMainBinding
import practice.registerforactivityresult.databinding.ActivityNameBinding

class NameActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_name)

        mBinding = ActivityNameBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        mBinding.buttonSubmit.setOnClickListener {
            val returnIntent = Intent()
            if (mBinding.textViewName.text.toString().isNotEmpty()) {
                returnIntent.putExtra(
                    "nameData",
                    mBinding.textViewName.text.toString()
                )
            }
            setResult(RESULT_OK, returnIntent)
            finish()
        }
        mBinding.buttonCancel.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
    }
}