package br.com.ibarra.docibarra.data.model

import com.google.gson.annotations.SerializedName

data class Doctor(
        @SerializedName("address") val address: String,
        @SerializedName("email") val email: String,
        @SerializedName("highlighted") val highlighted: Boolean,
        @SerializedName("id") val id: String,
        @SerializedName("integration") val integration: String,
        @SerializedName("lat") val lat: Double,
        @SerializedName("lng") val lng: Double,
        @SerializedName("name") val name: String,
        @SerializedName("photoId") val photoId: String,
        @SerializedName("rating") val rating: Double,
        @SerializedName("website") val website: String
)