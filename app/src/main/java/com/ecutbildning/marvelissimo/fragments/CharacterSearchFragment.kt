package com.ecutbildning.marvelissimo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.adapters.CharacterRecycleViewAdapter
import com.ecutbildning.marvelissimo.dtos.Character
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search.view.*
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.SearchView
import android.view.*
import com.ecutbildning.marvelissimo.services.paging.CharactersDataSource

private const val GRID_SPAN_COUNT = 3

class CharacterSearchFragment : Fragment(), ISearchFragment {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharacterRecycleViewAdapter by lazy {
        CharacterRecycleViewAdapter { character -> onItemClicked(character)}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        setUpRecycleView(rootView)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = menu?.findItem(R.id.navigation_search)?.actionView as SearchView
        searchView.isIconified = true
    }

    private fun onItemClicked(character: Character){
         activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.container, CharacterInfoFragment.newInstance(character), CharacterInfoFragment::class.java.name)
             ?.addToBackStack(CharacterInfoFragment::class.java.name)
            ?.commit()
    }

    private fun setUpRecycleView(rootView: View){
        CharactersDataSource.search = null
        val layoutManager = GridLayoutManager(activity, GRID_SPAN_COUNT)
        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        subscribeToList()
    }

    private fun subscribeToList() {
        val disposable = viewModel.characterList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.submitList(list)
            }
    }

    override fun makeSearch(search: String?) {
        CharactersDataSource.search = search
        adapter.currentList?.dataSource?.invalidate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharacterSearchFragment()
    }
}
