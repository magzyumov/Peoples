package ru.magzyumov.peoples.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "peoples")
class PeopleEntity (

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "email")
    var email: String?,

    @ColumnInfo(name = "first_name")
    var first_name: String?,

    @ColumnInfo(name = "last_name")
    var last_name: String?,

    @ColumnInfo(name = "avatar")
    var avatar: String?,
): Parcelable {

}
