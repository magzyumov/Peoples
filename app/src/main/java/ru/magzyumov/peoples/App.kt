package ru.magzyumov.peoples

import android.app.Application
import ru.magzyumov.peoples.di.AppComponent
import ru.magzyumov.peoples.di.AppModule
import ru.magzyumov.peoples.di.DaggerAppComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        component = generateAppComponent()
    }

    private fun generateAppComponent(): AppComponent{
        return DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object{
        private lateinit var component: AppComponent

        fun getComponent(): AppComponent = component
    }
}