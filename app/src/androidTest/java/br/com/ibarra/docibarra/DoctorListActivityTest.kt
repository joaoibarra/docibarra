package br.com.ibarra.docibarra

import androidx.paging.PagedList
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import br.com.ibarra.docibarra.data.DoctorListDataSource
import br.com.ibarra.docibarra.data.DoctorListDataSourceFactory
import br.com.ibarra.docibarra.data.LoginDataSource
import br.com.ibarra.docibarra.data.LoginRepository
import br.com.ibarra.docibarra.data.model.AuthUser
import br.com.ibarra.docibarra.data.model.Doctor
import br.com.ibarra.docibarra.data.remote.DocIbarraApi
import br.com.ibarra.docibarra.data.remote.LoginApi
import br.com.ibarra.docibarra.di.DocSearchModule
import br.com.ibarra.docibarra.di.GlideManager
import br.com.ibarra.docibarra.di.LoginModule
import br.com.ibarra.docibarra.di.docIbarraModule
import br.com.ibarra.docibarra.ui.doctor.DoctorListActivity
import br.com.ibarra.docibarra.ui.doctor.DoctorListAdapter
import br.com.ibarra.docibarra.ui.doctor.DoctorListViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Observable
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.SQLInvalidAuthorizationSpecException
import kotlin.math.sin

@LargeTest
@RunWith(JUnit4::class)
class DoctorListActivityTest: KoinTest {
    private lateinit var mockWebServer: MockWebServer

    @get:Rule
    var activityRule = ActivityTestRule(DoctorListActivity::class.java, true, false)

    @MockK(relaxUnitFun = true)
    lateinit var doctorListViewModel: DoctorListViewModel

////    @MockK(relaxUnitFun = true)
//    val doctorListAdapter: DoctorListAdapter by inject<DoctorListAdapter>()

    @MockK(relaxUnitFun = true)
    lateinit var doctorListAdapter: DoctorListAdapter

    @MockK(relaxUnitFun = true)
    lateinit var doctorListDataSourceFactory:DoctorListDataSourceFactory

    @MockK(relaxUnitFun = true)
    lateinit var glideManager:GlideManager

    @MockK(relaxUnitFun = true)
    lateinit var doctorListDataSource:DoctorListDataSource

    @MockK(relaxUnitFun = true)
    lateinit var docIbarraApi:DocIbarraApi

    @MockK(relaxUnitFun = true)
    lateinit var retrofit:Retrofit

    @MockK(relaxUnitFun = true)
    lateinit var loginApi:LoginApi

    @MockK(relaxUnitFun = true)
    lateinit var loginDataSource:LoginDataSource

    @MockK(relaxUnitFun = true)
    lateinit var pageConfig: PagedList.Config

    @MockK(relaxUnitFun = true)
    lateinit var pagedList: Observable<PagedList<Doctor>>

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8000)

        MockKAnnotations.init(this, relaxUnitFun = true)

//        every { doctorListViewModel.getAdapter() } returns doctorListAdapter
//        every { doctorListViewModel.doctors } returns pagedList

        loadKoinModules(module {
            single<Retrofit>{retrofit}
            single<LoginApi> {loginApi}
            single<LoginDataSource> {loginDataSource}
            single<DocIbarraApi>{docIbarraApi}
            factory<GlideManager> { glideManager }
            factory<DoctorListAdapter> { doctorListAdapter }
            single<DoctorListDataSource> {doctorListDataSource}
            single<DoctorListDataSourceFactory> { doctorListDataSourceFactory }
            single<PagedList.Config> { pageConfig }
            single<Observable<PagedList<Doctor>>> { pagedList }
            viewModel { doctorListViewModel }
        })

//        loadKoinModules(listOf(LoginModule, DocSearchModule))

//        declareMock<Retrofit>()
//        declareMock<LoginApi> ()
//        declareMock<LoginDataSource> ()
//
//        declareMock<String>()
//        declareMock<GlideManager>()
//        declareMock<DoctorListAdapter>()

//        declareMock<AuthUser>()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        stopKoin()
    }

    @Test
    fun whenScreenIsLoaded_ShouldShowDoctorsList() {
        arrange(activityRule) {
            mockWebServer(mockWebServer)
            launchActivity()
        }
        act {

        }
    }
}