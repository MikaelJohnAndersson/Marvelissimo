package com.ecutbildning.marvelissimo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import com.ecutbildning.marvelissimo.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search.view.*
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.view.*
import com.ecutbildning.marvelissimo.adapters.ComicRecycleViewAdapter
import com.ecutbildning.marvelissimo.dtos.Comic
import com.ecutbildning.marvelissimo.services.paging.ComicsDataSource

private const val GRID_SPAN_COUNT = 3

class ComicsSearchFragment : Fragment(), ISearchFragment {

    private val viewModel: ComicsViewModel by lazy {
        ViewModelProviders.of(this).get(ComicsViewModel::class.java)
    }

    private val adapter: ComicRecycleViewAdapter by lazy {
        ComicRecycleViewAdapter { comic -> onItemClicked(comic)}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        setUpRecycleView(rootView)
        return rootView
    }

    private fun onItemClicked(comic: Comic){
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, ComicInfoFragment.newInstance(comic), ComicInfoFragment::class.java.name)
            ?.addToBackStack(ComicInfoFragment::class.java.name)
            ?.commit()
    }

    private fun setUpRecycleView(rootView: View){
        ComicsDataSource.search = null
        val layoutManager = GridLayoutManager(activity, GRID_SPAN_COUNT)
        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        subscribeToList()
    }

    private fun subscribeToList() {
        val disposable = viewModel.comicList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.submitList(list)
            }
    }

    override fun makeSearch(search: String?) {
        ComicsDataSource.search = search
        adapter.currentList?.dataSource?.invalidate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ComicsSearchFragment()
    }
}
