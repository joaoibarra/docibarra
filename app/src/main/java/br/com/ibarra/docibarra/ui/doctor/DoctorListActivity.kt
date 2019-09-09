package br.com.ibarra.docibarra.ui.doctor

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ibarra.docibarra.R
import br.com.ibarra.docibarra.databinding.ActivityDoctorListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_doctor_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoctorListActivity: AppCompatActivity() {
    private val disposable = CompositeDisposable()

    private val doctorListViewModel: DoctorListViewModel by viewModel()

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    companion object {
        const val ACCESS_LOCATION_PERMISSION = 27
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val binding: ActivityDoctorListBinding = DataBindingUtil.setContentView(this@DoctorListActivity, R.layout.activity_doctor_list)
        binding.viewModel = doctorListViewModel
        rv_doctors.layoutManager = LinearLayoutManager(this)
        rv_doctors.adapter = doctorListViewModel.getAdapter()

    }

    override fun onStart() {
        super.onStart()
        checkUserPermission()
        handleIntent(intent)
    }

    fun setListData(): Disposable? {
        return doctorListViewModel.doctors?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { list ->
                            doctorListViewModel.getAdapter().submitList(list)
//                            hideProgressBar()
                        },
                        { e ->
                            Log.e("Error OnLoad", "Error", e)
                        }
                )
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.doctor_list_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            doctorListViewModel.doSearch(query)
            doctorListViewModel.getAdapter().submitList(null)
        }
    }

    private fun checkUserPermission() {
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION) ,
                    ACCESS_LOCATION_PERMISSION )

        } else {
            getUserLocation()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == ACCESS_LOCATION_PERMISSION
                && grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getUserLocation()

        }
    }

    private fun getUserLocation() {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location : Location? ->
            location?.let {
                doctorListViewModel.latitude = location.latitude
                doctorListViewModel.longitude = location.longitude
                doctorListViewModel.initDoctorsList()
                setListData()?.let { disposable.add(it) }
            }
        }?.addOnFailureListener {
            doctorListViewModel.initDoctorsList()
            setListData()?.let { disposable.add(it) }
        }

    }

//    fun showProgressBar() {
////        progressbar.visibility = View.VISIBLE
//        shimmer_layout.startShimmer()
//    }
//
//    fun hideProgressBar() {
////        progressbar.visibility = View.
//        shimmer_layout.stopShimmer()
//
//    }
}