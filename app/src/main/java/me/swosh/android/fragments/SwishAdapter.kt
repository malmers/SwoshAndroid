package me.swosh.android.fragments

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.swosh.android.R
import me.swosh.android.models.Swosh
import net.glxn.qrgen.android.QRCode

/**
 * Created by mikael on 2017-07-30.
 */

class SwishAdapter(context: Context, swoshList: ArrayList<Swosh>)
    : RecyclerView.Adapter<SwishAdapter.SwishViewHolder>() {

    private val swoshList: List<Swosh> = swoshList
    private val context: Context = context
    private lateinit var adapterListener: AdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwishViewHolder? {
        val layoutView = LayoutInflater.from(parent.context)
                .inflate(R.layout.swish_card, null)
        val viewHolder = SwishViewHolder(layoutView)

        Log.d("SwishAdapter", "context is AdapterListener: " + (context is AdapterListener))
        Log.d("SwishAdapter", "context is : " + (context))

        if(context is AdapterListener)
            adapterListener = context

        return viewHolder
    }

    override fun onBindViewHolder(holder: SwishViewHolder, position: Int) {

        val swosh: Swosh = swoshList[position]
        holder.itemView.setOnClickListener{ adapterListener.onCardClick(swosh) }
        holder.itemView.setOnLongClickListener{ adapterListener.onCardLongClick(swosh) }

        holder.messageText.text = swosh.message
        holder.expirationText.text = swosh.expireAfterSeconds.toString()
        holder.amountText.text = swosh.amount.toString()
        holder.qrImage.setImageBitmap(QRCode.from(swosh.url)
                .withSize(100,100)
                .withColor(context.resources.getColor(R.color.textColor), Color.TRANSPARENT)
                .bitmap())
    }

    override fun getItemCount(): Int {
        return swoshList.size
    }

    class SwishViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var amountText: TextView = itemView!!.findViewById(R.id.card_amount_text)
        var messageText: TextView = itemView!!.findViewById(R.id.card_message_text)
        var expirationText: TextView = itemView!!.findViewById(R.id.card_expiration_text)
        var qrImage: ImageView = itemView!!.findViewById(R.id.card_qr_image)
    }

    interface AdapterListener {
        fun onCardClick(swosh: Swosh)
        fun onCardLongClick(swosh: Swosh): Boolean
    }
}
