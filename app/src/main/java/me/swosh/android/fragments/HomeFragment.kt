package me.swosh.android.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.swosh.android.R
import me.swosh.android.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_home.*
import me.swosh.android.models.Swosh


class HomeFragment : Fragment() {

    private lateinit var listener: HomeFragmentListener
    private lateinit var swishAdapter: SwishAdapter
    private lateinit var layoutManager: LinearLayoutManager

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

        var list: ArrayList<Swosh> = ArrayList()
        /*list.add(Swosh("000", 100, "Test",
                600, "1337", "http://localhost"))*/

        swish_recycle_list.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)
        swish_recycle_list.layoutManager = layoutManager

        swishAdapter = SwishAdapter(context, list)
        swishAdapter.notifyDataSetChanged()

        //swishAdapter.setAdapterListener(context as MainActivity)
        swish_recycle_list.adapter = swishAdapter;

    }

    interface HomeFragmentListener {
        fun addClick()
    }
}
