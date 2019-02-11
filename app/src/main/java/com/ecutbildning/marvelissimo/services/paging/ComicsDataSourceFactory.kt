package com.ecutbildning.marvelissimo.services.paging

import android.arch.paging.DataSource
import com.ecutbildning.marvelissimo.dtos.Comic
import com.ecutbildning.marvelissimo.services.MarvelAPI
import io.reactivex.disposables.CompositeDisposable

class ComicsDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val marvelAPI: MarvelAPI
): DataSource.Factory<Int, Comic>() {

    override fun create(): DataSource<Int, Comic> {
        return ComicsDataSource(marvelAPI, compositeDisposable)
    }

}