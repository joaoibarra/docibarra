package br.com.ibarra.docibarra.di

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import br.com.ibarra.docibarra.BuildConfig
import br.com.ibarra.docibarra.data.DoctorListDataSourceFactory
import br.com.ibarra.docibarra.data.LoginDataSource
import br.com.ibarra.docibarra.data.LoginRepository
import br.com.ibarra.docibarra.data.model.Doctor
import br.com.ibarra.docibarra.data.remote.DocIbarraApi
import br.com.ibarra.docibarra.data.remote.LoginApi
import br.com.ibarra.docibarra.ui.doctor.DoctorListAdapter
import br.com.ibarra.docibarra.ui.doctor.DoctorListViewModel
import br.com.ibarra.docibarra.ui.login.LoginViewModel
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 15L
private const val READ_TIMEOUT = 15L

val LoginModule = module {
    single<Retrofit>(named("loginService")) {
        Retrofit.Builder()
                .baseUrl("https://auth.staging.vivy.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    single<LoginApi> { get<Retrofit>(named("loginService")).create(LoginApi::class.java) }

    single<LoginDataSource> { LoginRepository(get()) }
    single(named("accessToken")){ get<LoginDataSource>().getAuthUser()?.let { it.tokenType.plus(" ").plus(it.accessToken) } }

    viewModel { LoginViewModel(get()) }
}

val DocSearchNetworkModule = module {
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }

    single { GsonBuilder().create() }

    single {
        Interceptor { chain ->
            chain.proceed(chain.request().newBuilder().apply {
                get<LoginDataSource>().getAuthUser()?.let {
                    authUser -> header("Authorization",
                        authUser.tokenType.plus(" ").plus(authUser.accessToken))
                }
            }.build())
        }
    }

    single{
        OkHttpClient.Builder().apply {
            cache(get())
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(get<Interceptor>())
            addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
        }.build()
    }

    factory(named("glideManager")) {
        GlideManager(androidApplication().applicationContext, get(named("accessToken")))
    }

    factory { DoctorListAdapter(get(named("glideManager"))) }
}

val DocSearchModule = module {

    single<Retrofit>(named("mainService")) {
        Retrofit.Builder()
                .baseUrl("https://api.staging.vivy.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(get())
                .build()
    }
    single<DocIbarraApi> { get<Retrofit>(named("mainService")).create(DocIbarraApi::class.java) }

    single<DoctorListDataSourceFactory> { DoctorListDataSourceFactory(CompositeDisposable(), get()) }

    factory<PagedList.Config> {
        PagedList.Config.Builder()
                .setPageSize(DoctorListViewModel.PAGESIZE)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .build()
    }

    single<Observable<PagedList<Doctor>>> {
        RxPagedListBuilder(get<DoctorListDataSourceFactory>(), get<PagedList.Config>())
                .setFetchScheduler(Schedulers.io())
                .buildObservable()
                .cache()
    }

    single { LocationServices.getFusedLocationProviderClient(androidApplication().applicationContext)
    }

    viewModel { DoctorListViewModel(get(), get(), get()) }

}

val docIbarraModule = listOf(LoginModule, DocSearchNetworkModule, DocSearchModule)