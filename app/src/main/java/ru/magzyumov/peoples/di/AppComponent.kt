package ru.magzyumov.peoples.di

import dagger.Component
import ru.magzyumov.peoples.ui.base.BaseFragment
import ru.magzyumov.peoples.ui.main.MainActivity
import ru.magzyumov.peoples.ui.main.SplashActivity

import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(splashActivity: SplashActivity)

    fun inject(baseFragment: BaseFragment)


}