package hu.webuni.familybudget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.webuni.familybudget.databinding.ActivityMainBinding
import hu.webuni.familybudget.fragment.FragmentLogin
import hu.webuni.familybudget.fragment.FragmentMain

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        showFragmentByTag(FragmentMain.TAG)
    }

    private fun showFragmentByTag(tag: String) {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            if (FragmentMain.TAG == tag) {
                fragment = FragmentMain()
            } else if (FragmentLogin.TAG == tag) {
                fragment = FragmentLogin()
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