package br.com.lucramaisagenciadigital.registrapedidos

import android.app.Application
import br.com.lucramaisagenciadigital.registrapedidos.di.DepInjectionInitializer

open class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupContext(this)
        setupKoin()
    }

    open fun setupKoin() {
        DepInjectionInitializer.init(this)
    }

    companion object {
        private lateinit var application: Application
        private fun setupContext(app: Application) {
            application = app
        }
    }
}