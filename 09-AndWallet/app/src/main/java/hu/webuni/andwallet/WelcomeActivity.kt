package hu.webuni.andwallet

/**
 * PIN: 5738
 */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result as FuelResult
import com.github.kittinunf.fuel.json.responseJson
import hu.webuni.andwallet.databinding.ActivityWelcomeBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class WelcomeActivity : AppCompatActivity() {

    private lateinit var welcomeBinding: ActivityWelcomeBinding

    private var alertDialog: AlertDialog? = null

    private var loginURL = "https://api.paulolajos.hu/andwallet/pinlogin.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        welcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = welcomeBinding.root
        setContentView(view)

        welcomeBinding.btPin.setOnClickListener {
            if (welcomeBinding.etPin.text.length == 4) {
                try {
                    //Call login api script
                    loginWithApi()
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            else
                Toast.makeText(
                    this,
                    "Please enter 4 digit PIN code!",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun loginWithApi() {
        showSimpleProgressDialog(null, "Check PIN code from network...", false)

        try {
            Fuel.post(
                loginURL, listOf(
                    "pin" to welcomeBinding.etPin.text.toString(),
                )
            ).responseJson { _, response, result ->
                val res2 = result
                when (result) {
                    is FuelResult.Failure -> {
                        Toast.makeText(
                            this,
                            getErrorMessage(res2.get().content),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is FuelResult.Success -> {
                        try {
                            val jsonObject = JSONObject(result.get().content)

                            if (jsonObject.optString("status") == "true") {
                                //removeSimpleProgressDialog()  //will remove progress dialog

                                Toast.makeText(
                                    this,
                                    "Login Successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)

                                finish()
                            }
                            else
                                Toast.makeText(this, getErrorMessage(res2.get().content), Toast.LENGTH_SHORT)
                                    .show()

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }

                removeSimpleProgressDialog()  //will remove progress dialog

                Log.d("loginResponseJson", response.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {

        }
    }

    private fun getErrorMessage(response: String): String {
        try {
            val jsonObject = JSONObject(response)
            return jsonObject.getString("message")

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return "Not loading data"
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