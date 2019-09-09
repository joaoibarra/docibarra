package br.com.ibarra.docibarra.ui.login

import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ibarra.docibarra.R
import br.com.ibarra.docibarra.data.LoginDataSource
import br.com.ibarra.docibarra.data.model.PostAuthUser


class LoginViewModel(val loginDataSource: LoginDataSource) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    lateinit var postAuthUser: PostAuthUser

    var emailAddress: String = "androidChallenge@vivy.com"
        get
    var password : String = "88888888"
        get

    fun onClick(view: View) {
        postAuthUser = PostAuthUser(emailAddress, password)
        verifyLoginData(postAuthUser)
    }

    fun login() {
        loginDataSource.login(postAuthUser, {
            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = it.accessToken))
        }, {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        })
    }

    fun verifyLoginData(postAuthUser: PostAuthUser) {
        if (!isUserNameValid(postAuthUser.username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(postAuthUser.password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
