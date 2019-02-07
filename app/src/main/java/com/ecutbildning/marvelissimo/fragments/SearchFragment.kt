package com.ecutbildning.marvelissimo.fragments

interface SearchFragment {
  fun loadMoreData()
  fun makeSearch(search : String?)
}