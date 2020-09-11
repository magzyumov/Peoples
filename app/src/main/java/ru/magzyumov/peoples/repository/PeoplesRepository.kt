package ru.magzyumov.peoples.repository

import androidx.lifecycle.LiveData
import ru.magzyumov.peoples.data.entity.PeopleEntity


interface PeoplesRepository {

    fun getNetworkStatus(): LiveData<String>
    fun getListOfPeoples(): LiveData<List<PeopleEntity>>

    fun getAllPeoples()

    fun insertPeoples(peoples: List<PeopleEntity>)
    fun getPeoplesFromDb(): LiveData<List<PeopleEntity>>
    fun deletePeople (people: PeopleEntity)
    fun updatePeople(people: PeopleEntity)

}