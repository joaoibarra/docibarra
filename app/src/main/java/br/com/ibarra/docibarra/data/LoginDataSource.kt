package br.com.ibarra.docibarra.data

import br.com.ibarra.docibarra.data.model.AuthUser
import br.com.ibarra.docibarra.data.model.PostAuthUser

interface LoginDataSource {
    fun getAuthUser(): AuthUser?
    fun login(postAuthUser: PostAuthUser, success : (AuthUser) -> Unit, failure: () -> Unit)
}
