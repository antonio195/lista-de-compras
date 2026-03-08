package com.antoniocostadossantos.listadecompras.core.di

import android.app.Application
import androidx.room.Room
import com.antoniocostadossantos.listadecompras.data.database.AppDatabase
import com.antoniocostadossantos.listadecompras.data.database.ProductDao
import com.antoniocostadossantos.listadecompras.data.repositories.ProductRepositoryImpl
import com.antoniocostadossantos.listadecompras.domain.repositories.ProductRepository
import com.antoniocostadossantos.listadecompras.presenter.features.home.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    private val appModule = module {
        single<AppDatabase> {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "products-database"
            ).build()
        }
        factory<ProductDao> {
            get<AppDatabase>().productDao()
        }
        factory<ProductRepository> {
            ProductRepositoryImpl(get())
        }
        viewModelOf(::HomeViewModel)
    }
}