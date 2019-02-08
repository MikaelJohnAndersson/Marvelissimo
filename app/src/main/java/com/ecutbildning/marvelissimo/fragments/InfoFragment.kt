package com.ecutbildning.marvelissimo.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.ecutbildning.marvelissimo.R
import com.ecutbildning.marvelissimo.adapters.ExpansionPanelAdapter
import kotlinx.android.synthetic.main.fragment_info.view.*

abstract class InfoFragment : Fragment() {
    abstract val INFO_OBJECT: String

    internal var infoObject: Any? = null

    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    abstract val data: HashMap<String, List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            infoObject = it.getParcelable(INFO_OBJECT)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_info, container, false)

        expandableListView = rootView.expandableListView
        val listData = data
        if (expandableListView != null) {
            titleList = ArrayList(listData.keys)
            adapter = ExpansionPanelAdapter(activity as Context, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
        }


        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.info_top_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}