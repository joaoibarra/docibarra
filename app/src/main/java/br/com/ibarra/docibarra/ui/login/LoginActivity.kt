package br.com.ibarra.docibarra.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.ibarra.docibarra.R
import br.com.ibarra.docibarra.databinding.ActivityLoginBinding
import br.com.ibarra.docibarra.ui.doctor.DoctorListActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)
        binding.viewmodel = loginViewModel

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
               password.error = getString(loginState.passwordError)
            }

            showLoading()

            loginViewModel.login()
            hideSoftKeyboard()
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.success != null) {
                updateUiWithUser()
                val intent = Intent(this@LoginActivity, DoctorListActivity::class.java)
                startActivity(intent)
            } else if(loginResult.error != null) {
                showLoginFailed()
            }

            hideLoading()

        })
    }

    private fun showLoading() {
        loading_container.visibility = View.VISIBLE
        login.visibility = View.GONE
    }

    private fun hideLoading() {
        loading_container.visibility = View.GONE
        login.visibility = View.VISIBLE
    }

    private fun updateUiWithUser() {
        Snackbar.make(container, R.string.welcome, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoginFailed() {
        Snackbar.make(container, R.string.login_failed, Snackbar.LENGTH_LONG).show()
    }

    fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
