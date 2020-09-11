package ru.magzyumov.peoples.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.magzyumov.peoples.data.database.PeoplesDao
import ru.magzyumov.peoples.data.entity.PeopleEntity
import ru.magzyumov.peoples.data.response.PeoplesResponse
import ru.magzyumov.peoples.utils.PeoplesRequest
import javax.inject.Inject


class PeoplesRepositoryImpl @Inject constructor(
    private val peoplesDao: PeoplesDao,
    private val peoplesRequest: PeoplesRequest
): PeoplesRepository {
    private val netWorkStatusLiveData: MutableLiveData<String> = MutableLiveData()
    private val listOfPeoplesLiveData: MutableLiveData<List<PeopleEntity>> = MutableLiveData()

    override fun getNetworkStatus(): LiveData<String> = netWorkStatusLiveData
    override fun getListOfPeoples(): LiveData<List<PeopleEntity>> = listOfPeoplesLiveData


    override fun insertPeoples(peoples: List<PeopleEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            peoplesDao.insertPeoples(peoples)
        }
    }

    override fun getPeoplesFromDb(): LiveData<List<PeopleEntity>> {
        return peoplesDao.getPeoplesFromDB()
    }

    override fun deletePeople(people: PeopleEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            peoplesDao.deletePeople(people)
        }
    }

    override fun updatePeople(people: PeopleEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            peoplesDao.updatePeople(people)
        }
    }

    override fun getAllPeoples() {
        peoplesRequest.getPeoplesFromServer()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(parseResponse)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: DisposableSingleObserver<List<PeopleEntity>>() {
                override fun onSuccess(peoples: List<PeopleEntity>) {
                    listOfPeoplesLiveData.postValue(peoples)
                    insertPeoples(peoples)
                }
                override fun onError(e: Throwable) {
                    netWorkStatusLiveData.postValue(e.localizedMessage.orEmpty())
                }
            })
    }

    private val parseResponse: Function1<PeoplesResponse, List<PeopleEntity>> =
        { response: PeoplesResponse ->

            val result: MutableList<PeopleEntity> = ArrayList()

            response.data?.forEach {
                result.add(PeopleEntity(it.id, it.email, it.firstName, it.lastName, it.avatar))
            }
            result
        }
}
