package com.ryancode.jokeapp.mvvm.data.remote

import com.ryancode.jokeapp.mvvm.data.model.joke.JokeCategoryResponse
import com.ryancode.jokeapp.mvvm.data.model.joke.JokeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("categories")
    fun getCategories(): Single<JokeCategoryResponse>

    @GET("joke/{category}")
    fun getJokes(
        @Path("category") category: String,
        @Query("type") type: String,
        @Query("amount") amount :Int
    ): Single<JokeResponse>
}