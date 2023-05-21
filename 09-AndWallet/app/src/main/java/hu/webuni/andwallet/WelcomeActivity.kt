package hu.webuni.andwallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import hu.webuni.andwallet.databinding.ActivityWelcomeBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class WelcomeActivity : AppCompatActivity() {

    lateinit var welcomeBinding: ActivityWelcomeBinding

    private var alertDialog: AlertDialog? = null

    private var loginURL = "https://api.paulolajos.hu/andwallet/pinlogin.php"
    private val loginTask = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        welcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = welcomeBinding.root
        setContentView(view)

        welcomeBinding.btPin.setOnClickListener {
            try {
                //Call login api script
                login()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    private fun login() {
        showSimpleProgressDialog(null, "Loading...", false)

        try {
            Fuel.post(
                loginURL, listOf(
                    "pin" to welcomeBinding.etPin.text.toString(),
                )
            ).responseJson { _, response, result ->
                onTaskCompleted(result.get().content, loginTask)

                Log.d("loginResponseJson", response.toString())

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {

        }
    }

    private fun onTaskCompleted(response: String, task: Int) {
        Log.d("loginResponseJson", response)

        removeSimpleProgressDialog()  //will remove progress dialog

        when (task) {
            loginTask -> if (isSuccess(response)) {

                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

                finish()

                Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, getErrorMessage(response), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isSuccess(response: String): Boolean {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.optString("status") == "true"

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return false
    }

    private fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return "No data"
    }

    private fun showSimpleProgressDialog(
        title: String?,
        msg: String,
        isCancelable: Boolean
    ) {
        try {
            val builder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(isCancelable)
            alertDialog = builder.create()
            alertDialog!!.show()

        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()
        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun removeSimpleProgressDialog() {
        try {
            if (alertDialog != null) {
                if (alertDialog!!.isShowing) {
                    alertDialog!!.dismiss()
                    alertDialog = null
                }
            }
        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()

        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}