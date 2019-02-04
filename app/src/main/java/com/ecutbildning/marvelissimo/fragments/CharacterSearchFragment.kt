package com.ecutbildning.marvelissimo.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.adapters.CharacterRecycleViewAdapter
import com.ecutbildning.marvelissimo.dtos.Character
import com.ecutbildning.marvelissimo.dtos.Response
import com.ecutbildning.marvelissimo.services.MarvelAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_character_search.*
import kotlinx.android.synthetic.main.fragment_character_search.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CharacterSearchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CharacterSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CharacterSearchFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MarvelAPI.getService().getAllCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response: Response? ->
                if (response != null) {
                    val characterList : List<Character> = response.data.results
                    //Getting adapter and setting data source to character list from response
                    val adapter = recyclerView.adapter as CharacterRecycleViewAdapter
                    adapter.characters = characterList
                    adapter.notifyDataSetChanged()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_search, container, false)
        //Initializing recycleview with empty list
        //TODO: Better solution for initializing recyclerView?
        setUpRecycleView(mutableListOf(), rootView)
        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    private fun onItemClicked(character: Character){
        //TODO: Implement functionality on list click
    }

    private fun setUpRecycleView(characterList: List<Character>, rootView: View ){
        val layoutManager = GridLayoutManager(activity, 3)
        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = layoutManager
        val adapter = CharacterRecycleViewAdapter(activity as Context, characterList) { character -> onItemClicked(character)}
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = CharacterSearchFragment()
    }
}
