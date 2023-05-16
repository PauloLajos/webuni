package hu.webuni.familybudget.fragment

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import hu.webuni.familybudget.PreferenceHelper
import hu.webuni.familybudget.databinding.FragmentLoginBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class FragmentLogin : Fragment() {
    companion object {
        const val TAG = "TAG_FRAGMENT_LOGIN"
    }

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val loginBinding get() = _binding!!

    private var preferenceHelper: PreferenceHelper? = null
    private var alertDialog: AlertDialog? = null

    private var loginURL = "https://api.paulolajos.hu/familybudget/simplelogin.php"
    private val loginTask = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        preferenceHelper = PreferenceHelper(requireContext())

        loginBinding.tvRegister.setOnClickListener {

        }

        loginBinding.btnLogin.setOnClickListener {
            try {
                login()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return loginBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun login() {
        showSimpleProgressDialog(this, null, "Loading...", false)

        try {
            Fuel.post(
                loginURL, listOf(
                    "username" to loginBinding.etUsername.text.toString(),
                    "password" to loginBinding.etPassword.text.toString()
                )
            ).responseJson { request, response, result ->
                onTaskCompleted(result.get().content, loginTask)
            }
        } catch (e: Exception) {

        } finally {

        }
    }

    private fun onTaskCompleted(response: String, task: Int) {
        Log.d("loginResponseJson", response)

        removeSimpleProgressDialog()  //will remove progress dialog

        when (task) {
            loginTask -> if (isSuccess(response)) {
                saveInfo(response)

                val tag = FragmentMain.TAG
                var fragment = parentFragmentManager.findFragmentByTag(tag)
                if (fragment == null) {
                    if (FragmentMain.TAG == tag) {
                        fragment = FragmentMain()
                    }
                    else if (FragmentLogin.TAG == tag) {
                        fragment = FragmentLogin()
                    }
                }

                if (fragment != null) {
                    val ft = parentFragmentManager.beginTransaction()
                    ft.replace(hu.webuni.familybudget.R.id.layoutContainer, fragment, tag)
                    //ft.addToBackStack(null) // add fragment transaction to the back stack

                    ft.commit()
                }

                Toast.makeText(requireActivity(), "Login Successfully!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireActivity(), getErrorMessage(response), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun saveInfo(response: String) {

        preferenceHelper!!.putIsLogin(true)

        try {
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("status") == "true") {
                val dataArray = jsonObject.getJSONArray("data")
                for (i in 0 until dataArray.length()) {

                    val dataObj = dataArray.getJSONObject(i)
                    preferenceHelper!!.putName(dataObj.getString("name"))
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
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
        context: FragmentLogin,
        title: String?,
        msg: String,
        isCancelable: Boolean
    ) {
        try {
            val builder = AlertDialog.Builder(requireActivity())
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