package practice.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /****
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowInsetsControllerCompat(
            window, window.decorView)
        // Hide system bars
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        */


        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler(Looper.getMainLooper()).postDelayed({
            val intentSplash = Intent(this, MainActivity::class.java)
            startActivity(intentSplash)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}