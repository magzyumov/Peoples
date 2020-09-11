package ru.magzyumov.peoples.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.magzyumov.peoples.repository.PeoplesRepository
import ru.magzyumov.peoples.data.database.PeoplesDao
import ru.magzyumov.peoples.data.database.PeoplesDatabase
import ru.magzyumov.peoples.repository.PeoplesRepositoryImpl
import ru.magzyumov.peoples.ui.main.MainViewModel
import ru.magzyumov.peoples.utils.BASE_URL
import ru.magzyumov.peoples.utils.PeoplesRequest
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun providesRepository(peoplesDao: PeoplesDao, peoplesRequest: PeoplesRequest): PeoplesRepository {
        return PeoplesRepositoryImpl (peoplesDao, peoplesRequest)
    }

    @Provides
    fun providesViewModel(peoplesRepository: PeoplesRepository): MainViewModel {
        return MainViewModel (peoplesRepository)
    }

    @Singleton
    @Provides
    fun providesPeoplesDao(db: PeoplesDatabase): PeoplesDao {
        return db.peoplesDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): PeoplesDatabase {
        return Room.databaseBuilder(context, PeoplesDatabase::class.java, "peoples_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): PeoplesRequest {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(PeoplesRequest::class.java)
    }

}