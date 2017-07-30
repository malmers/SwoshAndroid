package me.swosh.android.fragments

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.swosh.android.R
import me.swosh.android.models.Swosh

/**
 * Created by mikael on 2017-07-30.
 */

class SwishAdapter(context: Context, swoshList: ArrayList<Swosh>)
    : RecyclerView.Adapter<SwishAdapter.SwishViewHolder>() {

    private val swoshList: List<Swosh> = swoshList
    private val context: Context = context // TODO add this as listener to card events

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwishViewHolder? {
        val layoutView = LayoutInflater.from(parent.context)
                .inflate(R.layout.swish_card, null)
        val viewHolder = SwishViewHolder(layoutView)

        return viewHolder
    }

    override fun onBindViewHolder(holder: SwishViewHolder, position: Int) {

        val swosh: Swosh = swoshList[position]
        holder.amountText.text = swosh.amount.toString()
    }

    override fun getItemCount(): Int {
        return swoshList.size
    }

    class SwishViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var amountText: TextView = itemView!!.findViewById(R.id.card_amount_text)
    }
}
