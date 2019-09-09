package br.com.ibarra.docibarra.data.remote

import br.com.ibarra.docibarra.data.model.SearchDoctorResult
import io.reactivex.Observable
import retrofit2.http.*

interface DocIbarraApi {
    @Headers(
            "Accept: application/json"
    )
    @GET("/api/users/me/doctors")
    fun listDoctors(
            @Query("lastKey") lastKey: String = "",
            @Query("search") search: String = "",
            @Query("lat") lat: Double = 52.534709,
            @Query("latDelta") latDelta: Double = 0.0122858883093357,
            @Query("lng") lng: Double = 13.3976972,
            @Query("lngDelta") lngDelta: Double = 0.0151786495888473,
            @Query("location") location: String = "Neukölln, Berlin"
    ) : Observable<SearchDoctorResult>

    @Headers(
            "Accept: application/json"
    )
    @GET("/api/doctors/{doctorId}/keys/profilepictures")
    fun getDoctorPicture(
            @Path("doctorId") doctorId: String = ""
    ) : Observable<SearchDoctorResult>


}