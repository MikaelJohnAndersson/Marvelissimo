package com.ecutbildning.marvelissimo.services.paging

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.ecutbildning.marvelissimo.dtos.Comic
import com.ecutbildning.marvelissimo.services.MarvelAPI
import io.reactivex.disposables.CompositeDisposable

class ComicsDataSource(
    private val marvelAPI: MarvelAPI,
    private val compositeDisposable: CompositeDisposable
): PageKeyedDataSource<Int, Comic>(){

    companion object {
        var search : String? = null
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Comic>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Comic>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Comic>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage : Int,
                                 adjacentPage : Int,
                                 requestedLoadSize : Int,
                                 initialCallBack: LoadInitialCallback<Int, Comic>?,
                                 callBack:  LoadCallback<Int, Comic>?){
        compositeDisposable.add(
            marvelAPI.getComics(requestedPage * requestedLoadSize, search)
                .subscribe { response ->
                    Log.d("COMICS_DATA_SRC", "Loading page $requestedPage")
                    initialCallBack?.onResult(response.data.results, null, adjacentPage)
                    callBack?.onResult(response.data.results, adjacentPage)
                }
        )
    }
}