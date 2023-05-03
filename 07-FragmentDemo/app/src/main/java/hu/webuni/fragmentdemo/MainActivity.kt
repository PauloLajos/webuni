package hu.webuni.fragmentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.webuni.fragmentdemo.databinding.ActivityMainBinding
import hu.webuni.fragmentdemo.fragment.FragmentDetails
import hu.webuni.fragmentdemo.fragment.FragmentMain

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.btnMain.setOnClickListener {
            showFragmentByTag(FragmentMain.TAG)
        }
        mainBinding.btnDetails.setOnClickListener {
            showFragmentByTag(FragmentDetails.TAG)
        }
    }

    private fun showFragmentByTag(tag: String) {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            if (FragmentMain.TAG == tag) {
                fragment = FragmentMain()
            } else if (FragmentDetails.TAG == tag) {
                fragment = FragmentDetails()
            }
        }

        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.layoutContainer, fragment, tag)

            ft.addToBackStack(null) // add fragment transaction to the back stack

            ft.commit()
        }
    }
}