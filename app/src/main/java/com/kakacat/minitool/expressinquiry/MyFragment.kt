package com.kakacat.minitool.expressinquiry

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.RecycleViewListener
import com.kakacat.minitool.expressinquiry.activity.DeliveryDetailActivity
import com.kakacat.minitool.expressinquiry.adapter.DeliveryAdapter
import com.kakacat.minitool.expressinquiry.model.Delivery
import java.util.*

class MyFragment(private val deliveryList: List<Delivery>) : Fragment() {

    lateinit var adapter : DeliveryAdapter
        private set
    private var intent: Intent? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv)
        adapter = DeliveryAdapter(deliveryList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : RecycleViewListener.OnItemClick{
            override fun onClick(v: View?, position: Int) {
                startDetailActivity(deliveryList[position])
            }
        })
        return view
    }

    private fun startDetailActivity(delivery: Delivery) {
        if (intent == null) {
            intent = Intent()
            intent!!.setClass(context!!, DeliveryDetailActivity::class.java)
        }
        intent!!.removeExtra("delivery")
        intent!!.putExtra("delivery", delivery)
        startActivity(intent)
    }
}