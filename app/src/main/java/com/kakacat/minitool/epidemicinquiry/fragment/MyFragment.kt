package com.kakacat.minitool.epidemicinquiry.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.GridLayout
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.kakacat.minitool.R
import com.kakacat.minitool.epidemicinquiry.Contract
import com.kakacat.minitool.epidemicinquiry.TitleView
import com.kakacat.minitool.epidemicinquiry.adapter.DomesticAdapter

class MyFragment(private val presenter: Contract.Presenter) : Fragment() {

    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: DomesticAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.myfragment_layout1, container, false)

        fun initTitleView() {
            val titleViewContainer = view.findViewById<GridLayout>(R.id.title_view_container)
            for (i in titles.indices) {
                val titleView = TitleView(context!!)
                titleView.setTitle(titles[i])
                titleView.setCountColor(countTextColors[i])
                titleView.setCount(0)
                titleViewContainer.addView(titleView)
            }
        }

        fun initExpandableListView() {
            expandableListView = view.findViewById(R.id.expand_list_view)
            adapter = DomesticAdapter(presenter.groupList!!)
            expandableListView.setAdapter(adapter)
        }

        initTitleView()
        initExpandableListView()
        return view
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView?) {
        val listAdapter = listView!!.adapter ?: return
        var totalHeight = 0
        var i = 0
        val len = listAdapter.count
        while (i < len) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
            i++
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }

    fun updateView() {
        adapter.notifyDataSetChanged()
        setListViewHeightBasedOnChildren(expandableListView)
    }

    companion object {
        private val titles = arrayOf("现有确诊", "现有疑似")
        private val countTextColors = intArrayOf(R.color.light4, R.color.light2)
    }
}