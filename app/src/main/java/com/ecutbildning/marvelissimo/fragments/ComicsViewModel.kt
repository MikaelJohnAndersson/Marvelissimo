package com.ecutbildning.marvelissimo.fragments

import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.ecutbildning.marvelissimo.services.MarvelAPI
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import android.arch.paging.RxPagedListBuilder
import com.ecutbildning.marvelissimo.dtos.Comic
import com.ecutbildning.marvelissimo.services.paging.ComicsDataSourceFactory

class ComicsViewModel : ViewModel() {

    var comicList: Observable<PagedList<Comic>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 20

    private var sourceFactory: ComicsDataSourceFactory

    init {
        sourceFactory = ComicsDataSourceFactory(compositeDisposable, MarvelAPI.getService())

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        comicList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}