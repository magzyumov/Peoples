package ru.magzyumov.peoples.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.magzyumov.peoples.data.entity.PeopleEntity


@Dao
interface PeoplesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(people: PeopleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeoples(peoples: List<PeopleEntity>)

    @Query("SELECT * FROM peoples")
    fun getPeoplesFromDB(): LiveData<List<PeopleEntity>>

    @Delete
    fun deletePeople(people: PeopleEntity)

    @Update
    fun updatePeople(people: PeopleEntity)
}