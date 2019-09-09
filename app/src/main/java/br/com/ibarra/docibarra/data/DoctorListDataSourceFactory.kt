package br.com.ibarra.docibarra.data

import androidx.paging.DataSource
import br.com.ibarra.docibarra.data.model.Doctor
import br.com.ibarra.docibarra.data.model.SearchDoctorQuery
import br.com.ibarra.docibarra.data.remote.DocIbarraApi
import io.reactivex.disposables.CompositeDisposable

class DoctorListDataSourceFactory(
        val compositeDisposable: CompositeDisposable,
        private val docIbarraApi: DocIbarraApi
) : DataSource.Factory<String, Doctor>() {
    lateinit var doctorListDataSource: DataSource<String, Doctor>
    var searchDoctorQuery: SearchDoctorQuery? = null

    override fun create(): DataSource<String, Doctor> {
        doctorListDataSource = DoctorListDataSource(docIbarraApi, compositeDisposable, searchDoctorQuery)
        return doctorListDataSource
    }

    fun setQuerySearch(searchText: String, latitude: Double, longitude: Double) {
        searchDoctorQuery = SearchDoctorQuery(searchText, latitude, longitude)
    }

    fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun invalidateDataSource() {
        if(::doctorListDataSource.isInitialized){
            doctorListDataSource.invalidate()
        }
    }

}