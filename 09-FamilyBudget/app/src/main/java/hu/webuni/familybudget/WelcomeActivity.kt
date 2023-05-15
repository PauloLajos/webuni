package hu.webuni.familybudget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.webuni.familybudget.data.PreferenceHelper
import hu.webuni.familybudget.databinding.ActivityWelcomeBinding


class WelcomeActivity : AppCompatActivity() {

    lateinit var welcomeBinding: ActivityWelcomeBinding

    companion object {
        const val BASE_URL = "https://api.paulolajos.hu/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        welcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = welcomeBinding.root
        setContentView(view)

        val preferenceHelper = PreferenceHelper(this)

        welcomeBinding.tvname.text = "Welcome ${preferenceHelper.getName()}"

        welcomeBinding.btnLogout.setOnClickListener {
            preferenceHelper.putIsLogin(false)

            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            finish()
        }
    }
}