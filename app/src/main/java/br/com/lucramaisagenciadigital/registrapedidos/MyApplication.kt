package br.com.lucramaisagenciadigital.registrapedidos

import android.app.Application
import br.com.lucramaisagenciadigital.registrapedidos.di.DepInjectionInitializer

open class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        DepInjectionInitializer.init(this)
    }
}