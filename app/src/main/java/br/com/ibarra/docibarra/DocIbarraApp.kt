package br.com.ibarra.docibarra

import android.app.Application
import br.com.ibarra.docibarra.di.docIbarraModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DocIbarraApp : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DocIbarraApp)
            modules(docIbarraModule)
        }

    }
}