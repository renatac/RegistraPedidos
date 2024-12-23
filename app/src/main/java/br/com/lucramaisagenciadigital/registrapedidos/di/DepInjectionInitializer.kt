package br.com.lucramaisagenciadigital.registrapedidos.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

object DepInjectionInitializer {

    fun init(application: Application) {
        startKoin {
            androidLogger()
            androidContext(application)
            modules(getUserDataModules())
        }
    }
}