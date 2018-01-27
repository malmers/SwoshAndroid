package me.swosh.android.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.swosh.android.R
import me.swosh.android.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_home.*
import me.swosh.android.data.HistoryStorage

class HomeFragment : Fragment() {

    private lateinit var listener: HomeFragmentListener
    private lateinit var swishAdapter: SwishAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var history: HistoryStorage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is MainActivity) {
            context.supportActionBar!!.setTitle(getString(R.string.ACTIONBAR_TITLE_YOUR_SWISH_LINKS))
            context.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }

        if(context is HomeFragmentListener)
            listener = context
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_button.setOnClickListener {
            listener.addClick()
        }

        val swoshList = history.getSwoshList()
        if(swoshList.isNotEmpty())
            info_box.visibility = View.GONE

        swish_recycle_list.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)
        swish_recycle_list.layoutManager = layoutManager

        swishAdapter = SwishAdapter(context, swoshList)

        //swishAdapter.setAdapterListener(context as MainActivity)
        swish_recycle_list.adapter = swishAdapter

    }

    fun setHistory(history: HistoryStorage) {
        this.history = history
    }

    fun refreshSwoshList () {

        if(info_box != null && history.getSwoshList().isEmpty()) {
            info_box.visibility = View.VISIBLE
        } else {
            info_box.visibility = View.GONE
        }

        if(swishAdapter != null) {
            swishAdapter = SwishAdapter(context, history.getSwoshList())
            swish_recycle_list.adapter = swishAdapter
            swishAdapter.notifyDataSetChanged()
        }
    }

    interface HomeFragmentListener {
        fun addClick()
    }

}
