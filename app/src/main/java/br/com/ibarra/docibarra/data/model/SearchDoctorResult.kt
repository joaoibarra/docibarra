package br.com.ibarra.docibarra.data.model

import com.google.gson.annotations.SerializedName

data class SearchDoctorResult (
        @SerializedName("doctors") val doctors: List<Doctor>,
        @SerializedName("lastKey") val lastKey: String? = null
){

}