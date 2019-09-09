package br.com.ibarra.docibarra.ui.doctor

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import br.com.ibarra.docibarra.data.DoctorListDataSourceFactory
import br.com.ibarra.docibarra.data.model.Doctor
import io.reactivex.Observable

class DoctorListViewModel(private val doctorListDataSourceFactory: DoctorListDataSourceFactory,
                          private val adapter: DoctorListAdapter,
                          private val pagedList: Observable<PagedList<Doctor>>) : ViewModel() {

    var doctors: Observable<PagedList<Doctor>>? = null

    var latitude: Double = 52.534709
        get
    var longitude : Double = 13.3976972
        get

    companion object{
        const val PAGESIZE = 20
    }

    fun initDoctorsList() {
        doctorListDataSourceFactory.setQuerySearch("", latitude, longitude)
        doctors = loadData()
    }

    fun loadData(): Observable<PagedList<Doctor>>  {
        return pagedList
    }

    fun doSearch(searchText: String) {
        doctorListDataSourceFactory.doctorListDataSource.invalidate()
        doctorListDataSourceFactory.setQuerySearch(searchText, latitude, longitude)
        doctors = loadData()
    }

    fun getAdapter(): DoctorListAdapter{
        return adapter
    }

    override fun onCleared() {
        super.onCleared()
        doctorListDataSourceFactory.onCleared()
    }
}