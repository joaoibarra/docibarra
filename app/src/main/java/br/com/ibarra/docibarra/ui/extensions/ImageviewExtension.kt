package br.com.ibarra.docibarra.ui.extensions

import android.widget.ImageView
//import br.com.ibarra.docibarra.GlideApp
//import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadCircle(url: String?) {

//    GlideApp.with(context)
//            .load(url)
//            .into(this)

//    val requestOptions = RequestOptions()
//    requestOptions.fitCenter()
//    Glide.with(context)
//            .asDrawable()
//            .apply(RequestOptions.circleCropTransform())
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .load(url)
//            .into(this)

//    val requestOptions = RequestOptions()
//    requestOptions.fitCenter()
//
//    authorization?.let{
//        val glideUrl = GlideUrl(url,
//                LazyHeaders.Builder()
//                        .addHeader("Authorization", authorization)
//                        .addHeader("Accept", "application/json")
//                        .build())
//        Glide.with(context)
//                .asDrawable()
//                .apply(RequestOptions.circleCropTransform())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .load(glideUrl)
//                .into(this)
//    }
}