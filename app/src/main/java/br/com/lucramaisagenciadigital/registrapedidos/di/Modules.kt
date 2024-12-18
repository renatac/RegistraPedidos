package br.com.lucramaisagenciadigital.registrapedidos.di

import br.com.lucramaisagenciadigital.registrapedidos.database.AppDatabase
import br.com.lucramaisagenciadigital.registrapedidos.domain.Repository
import br.com.lucramaisagenciadigital.registrapedidos.domain.RepositoryImpl
import br.com.lucramaisagenciadigital.registrapedidos.database.getDatabase
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


private val databaseModule = module {
    single { getDatabase(androidContext())}

    factory { get<AppDatabase>().getDao() }
}

private val dataModule = module {
    factory<Repository> { RepositoryImpl(database = get()) }
}

private val presentationModule = module {
    single<UserDataViewModel> { UserDataViewModel(repository = get()) }
}

fun getUserDataModules() = listOf(databaseModule, dataModule, presentationModule)
