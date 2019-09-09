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

    var searchText : String = ""
    var latitude: Double = 52.534709
    var longitude : Double = 13.3976972

    companion object{
        const val PAGESIZE = 20
    }

    fun initDoctorsList() {
        doctors = null
        adapter.submitList(null)
        doctorListDataSourceFactory.invalidateDataSource()
        doctorListDataSourceFactory.setQuerySearch(searchText, latitude, longitude)
        doctors = loadData()
    }

    fun loadData(): Observable<PagedList<Doctor>>  {
        return pagedList
    }

    fun getAdapter(): DoctorListAdapter{
        return adapter
    }

    override fun onCleared() {
        super.onCleared()
        doctorListDataSourceFactory.onCleared()
    }
}