package br.com.ibarra.docibarra.ui.doctor

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import br.com.ibarra.docibarra.data.DoctorListDataSourceFactory
import br.com.ibarra.docibarra.data.model.Doctor
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

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

    init {
//        doctorListDataSourceFactory.setQuerySearch("", latitude, longitude)
//        getUserLocation()
    }

    fun initDoctorsList() {
        doctorListDataSourceFactory.setQuerySearch("", latitude, longitude)
        doctors = loadData()
    }

    fun loadData(): Observable<PagedList<Doctor>>  {
        return pagedList
    }

//    fun loadData(): Observable<PagedList<Doctor>> {
//
//        val config = PagedList.Config.Builder()
//                .setPageSize(PAGESIZE)
//                .setEnablePlaceholders(true)
//                .setInitialLoadSizeHint(10)
//                .build()
//
//        return RxPagedListBuilder(doctorListDataSourceFactory, config)
//                .setFetchScheduler(Schedulers.io())
//                .buildObservable()
//                .cache()
//    }

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
//        if (!doctorListDataSourceFactory.compositeDisposable.isDisposed) {
//            compositeDisposable.dispose()
//        }
    }
}