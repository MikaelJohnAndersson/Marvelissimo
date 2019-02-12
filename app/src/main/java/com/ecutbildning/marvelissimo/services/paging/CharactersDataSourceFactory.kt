package com.ecutbildning.marvelissimo.services.paging

import android.arch.paging.DataSource
import com.ecutbildning.marvelissimo.dtos.Character
import com.ecutbildning.marvelissimo.services.MarvelAPI
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val marvelAPI: MarvelAPI
): DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelAPI, compositeDisposable)
    }

}