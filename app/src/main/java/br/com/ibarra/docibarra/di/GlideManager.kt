package br.com.ibarra.docibarra.di

import android.content.Context
import android.widget.ImageView
import br.com.ibarra.docibarra.R
import br.com.ibarra.docibarra.data.model.AuthUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import org.koin.dsl.koinApplication

class GlideManager(private var context: Context, private val authorization: String) {
//    private var url: String = ""
    private var doctorId: String = ""

//    fun setUrl(url: String) : GlideManager {
//        this.url = url
//        return this
//    }

    fun setDoctorId(doctorId: String) : GlideManager {
        this.doctorId = doctorId
        return this
    }

    fun loadDoctorImageInto(imageview: ImageView) {
        if(doctorId.isNotEmpty()){
            setRemoteImage(imageview)
        } else {
            setDefaultImage(imageview)
        }

    }

    private fun setRemoteImage(imageview: ImageView) {
        Glide.with(context)
                .asDrawable()
                .transform(CircleCrop())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(getGlideUrlHeader())
                .placeholder(getRandomPlaceholder())
                .into(imageview)
        doctorId = ""
    }

    private fun setDefaultImage(imageview: ImageView) {
//        imageview.setImageResource(R.drawable.ic_doctors_bag)
        Glide.with(context)
                .asDrawable()
                .transform(CircleCrop())
                .placeholder(getRandomPlaceholder())
                .into(imageview)
    }

    private fun getGlideUrlHeader(): GlideUrl {
        return GlideUrl("https://api.staging.vivy.com/api/doctors/$doctorId/keys/profilepictures",
                LazyHeaders.Builder()
                        .addHeader("Authorization", authorization)
                        .addHeader("Accept", "application/json")
                        .build())
    }

    private fun getRandomPlaceholder(): Int{
        val list = listOf(R.drawable.ic_101_stethoscope, R.drawable.ic_101_medical_1, R.drawable.ic_101_first_aid_kit)
        return list.random()
    }
}