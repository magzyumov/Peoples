package ru.magzyumov.peoples.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.magzyumov.peoples.data.entity.PeopleEntity

@Database(entities = [PeopleEntity::class], version = 1, exportSchema = false)
abstract class PeoplesDatabase: RoomDatabase() {
    abstract fun peoplesDao(): PeoplesDao
}