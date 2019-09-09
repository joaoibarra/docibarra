package br.com.ibarra.docibarra.data

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import br.com.ibarra.docibarra.data.model.Doctor
import br.com.ibarra.docibarra.data.model.SearchDoctorQuery
import br.com.ibarra.docibarra.data.remote.DocIbarraApi
import io.reactivex.disposables.CompositeDisposable

class DoctorListDataSource (
        private val docIbarraApi: DocIbarraApi,
        private val compositeDisposable: CompositeDisposable,
        private val searchDoctorQuery: SearchDoctorQuery?
) : ItemKeyedDataSource<String, Doctor>() {
    private var lastKey = ""

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Doctor>) {
        createObservable(callback, null)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Doctor>) {
        createObservable( null, callback)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Doctor>) {
        // Empty method
    }

    override fun getKey(item: Doctor): String {
        return lastKey
    }

    private fun createObservable(
            initialCallback: LoadInitialCallback<Doctor>?,
            callback: LoadCallback<Doctor>?
    ) {
        compositeDisposable.add(
            docIbarraApi.listDoctors(
                lastKey,
                    searchDoctorQuery?.searchText.orEmpty(),
                    searchDoctorQuery?.latitude?:orDefaultLatitude(),
                    searchDoctorQuery?.longitude?:orDefaultLongitude()
                )
                .subscribe(
                    { response ->
                        if(response.lastKey != null) {
                            lastKey = response.lastKey
                        } else {
                            lastKey = "ERROR"
                        }
                        initialCallback?.onResult(response.doctors)
                        callback?.onResult(response.doctors)
                    },
                    { t ->
                        Log.d("Error On Load", "Error loading page:", t)
                    }
                )
        )
    }

    private fun orDefaultLatitude(): Double {
        return 52.534709
    }

    private fun orDefaultLongitude(): Double {
        return 13.3976972
    }
}