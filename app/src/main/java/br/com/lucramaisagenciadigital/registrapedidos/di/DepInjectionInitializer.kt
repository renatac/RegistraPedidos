package br.com.lucramaisagenciadigital.registrapedidos.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

object DepInjectionInitializer {

    fun init(application: Application) {
        startKoin {
            printLogger(Level.ERROR)
            androidContext(application)
            modules(getUserDataModules())
        }
    }
}