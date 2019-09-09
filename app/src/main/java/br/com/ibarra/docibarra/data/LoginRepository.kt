package br.com.ibarra.docibarra.data

import br.com.ibarra.docibarra.data.model.AuthUser
import br.com.ibarra.docibarra.data.model.PostAuthUser
import br.com.ibarra.docibarra.data.remote.LoginApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val loginApi: LoginApi) : LoginDataSource {

    var _authUser: AuthUser? = null

    override fun getAuthUser(): AuthUser? {
        return _authUser
    }

    override fun login(postAuthUser: PostAuthUser, success : (AuthUser) -> Unit, failure: () -> Unit) {
        val call = loginApi.login(postAuthUser.username, postAuthUser.password)
        call.enqueue(object : Callback<AuthUser> {
            override fun onFailure(call: Call<AuthUser>, t: Throwable) {
                failure()
            }

            override fun onResponse(call: Call<AuthUser>, response: Response<AuthUser>) {
                if(response.isSuccessful) {
                    _authUser = response.body() as AuthUser
                    _authUser?.let { success(it)}?:failure()
                } else {
                    failure()
                }
            }
        })
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

