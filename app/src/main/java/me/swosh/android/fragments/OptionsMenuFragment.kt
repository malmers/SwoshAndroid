package me.swosh.android.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.long_click_menu.*
import me.swosh.android.R
import me.swosh.android.models.Swosh
import net.glxn.qrgen.android.QRCode

/**
 * Created by mikael on 2017-08-06.
 */
class OptionsMenuFragment : BottomSheetDialogFragment() {

    private lateinit var swosh: Swosh

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.long_click_menu, container, false)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menu_qr.setImageBitmap(QRCode.from(swosh.url)
                .withSize(100,100)
                .withColor(Color.WHITE, Color.TRANSPARENT)
                .bitmap())
        menu_amount.text = swosh.amount.toString()
        menu_message.text = swosh.message
    }

    fun setSwosh(swosh: Swosh) {
        this.swosh = swosh
    }
}