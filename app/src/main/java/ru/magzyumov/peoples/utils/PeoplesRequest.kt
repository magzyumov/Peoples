package ru.magzyumov.peoples.utils

import io.reactivex.Single
import retrofit2.http.GET
import ru.magzyumov.peoples.data.response.PeoplesResponse

interface PeoplesRequest {
    @GET("users?page=2")
    fun getPeoplesFromServer(): Single<PeoplesResponse>
}