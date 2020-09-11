package ru.magzyumov.peoples.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.magzyumov.peoples.data.entity.PeopleEntity
import ru.magzyumov.peoples.repository.PeoplesRepository
import javax.inject.Inject


class MainViewModel
@Inject constructor(
    private val peoplesRepository: PeoplesRepository
): ViewModel() {

    fun getPeoplesFromServer(){
        peoplesRepository.getAllPeoples()
    }

    fun getPeoplesFromDB(): LiveData<List<PeopleEntity>>{
        return peoplesRepository.getPeoplesFromDb()
    }

    fun deletePeople(people: PeopleEntity) {
        peoplesRepository.deletePeople(people)
    }

    fun updatePeople(people: PeopleEntity) {
        peoplesRepository.updatePeople(people)
    }

    fun getNetWorkStatus(): LiveData<String>{
        return peoplesRepository.getNetworkStatus()
    }
}