package com.ryancode.jokeapp.mvvm.data.repository

import com.ryancode.jokeapp.mvvm.data.model.joke.JokeCategoryResponse
import com.ryancode.jokeapp.mvvm.data.model.joke.JokeResponse
import com.ryancode.jokeapp.mvvm.data.remote.ApiService
import com.ryancode.jokeapp.mvvm.utils.NetworkErrorHandler
import com.ryancode.jokeapp.mvvm.utils.Resource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JokeRepository
@Inject
constructor(
    private val apiService: ApiService
){


    fun getCategories(): Single<Resource<JokeCategoryResponse>> {
        return apiService.getCategories()
            .subscribeOn(Schedulers.io())
            .map<Resource<JokeCategoryResponse>> { Resource.Success(it) }
            .onErrorReturn { Resource.Error(NetworkErrorHandler.handleError(it)) }
            .observeOn(AndroidSchedulers.mainThread())
    }



    fun getJokes(category: String, amount: Int): Single<Resource<JokeResponse>> {
        return apiService.getJokes(category,"single", amount, )
            .subscribeOn(Schedulers.io())
            .map<Resource<JokeResponse>> { Resource.Success(it) }
            .onErrorResumeNext { throwable: Throwable ->
                apiService.getJokes(category,"twopart", amount)
                    .map<Resource<JokeResponse>> { Resource.Success(it) }
                    .onErrorReturn { Resource.Error(NetworkErrorHandler.handleError(it)) }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }


}