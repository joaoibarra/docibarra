package br.com.ibarra.docibarra.data.model

class SearchDoctorQuery (
        val searchText: String = "",
        val latitude: Double? = null,
        val longitude: Double? = null
)