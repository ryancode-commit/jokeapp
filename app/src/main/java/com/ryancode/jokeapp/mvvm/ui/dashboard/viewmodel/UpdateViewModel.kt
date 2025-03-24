package com.ryancode.jokeapp.mvvm.ui.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryancode.jokeapp.mvvm.data.model.joke.JokeCategoryResponse
import com.ryancode.jokeapp.mvvm.data.model.joke.JokeResponse
import com.ryancode.jokeapp.mvvm.data.repository.JokeRepository
import com.ryancode.jokeapp.mvvm.utils.NetworkErrorHandler
import com.ryancode.jokeapp.mvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel
@Inject
constructor(
    private val repository: JokeRepository
): ViewModel(){
    private val disposables = CompositeDisposable()



    private val _getJokesCategory = MutableLiveData<Resource<JokeCategoryResponse>>(Resource.Loading())
    val getJokesCategory : LiveData<Resource<JokeCategoryResponse>> get() = _getJokesCategory

    private val _getJokes = MutableLiveData<Resource<JokeResponse>>(Resource.Loading())
    val getJokes : LiveData<Resource<JokeResponse>> get() = _getJokes




    fun getJokesCategory(){
        _getJokesCategory.value = Resource.Loading()

        repository.getCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {response ->
                    _getJokesCategory.value = response
                },
                {throwable ->
                    _getJokesCategory.value = Resource.Error(NetworkErrorHandler.handleError(throwable))
                }
            ).addTo(disposables)
    }

    fun getJokes(
        category : String,
        amount : Int
    ){
        _getJokes.value = Resource.Loading()
        repository.getJokes(
            category = category,
            amount = amount
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {response ->
                    _getJokes.value = response
                },
                {throwable ->
                    _getJokes.value = Resource.Error(NetworkErrorHandler.handleError(throwable))
                }
            ).addTo(disposables)
    }




    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    private fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(this)
    }
}