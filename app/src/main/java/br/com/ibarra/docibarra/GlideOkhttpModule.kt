package br.com.ibarra.docibarra

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

//@GlideModule
//class GlideOkhttpModule : AppGlideModule(){
////    lateinit var  lol: OkHttpClient
////    constructor(client: OkHttpClient) : this() {
////       lol = client
////
////    }
//
//
//    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
////        loadKoinModules(listOf(NetworkModule))
////        val lol: OkHttpClient by inject(named("okhttpclient"))
////        val factory = OkHttpUrlLoader.Factory(lol)
////        registry.replace(GlideUrl::class.java, InputStream::class.java, factory)
//        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
//
//
//    }
//}
