package me.swosh.android.fragments

import android.content.Context
import android.content.Intent
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
    private lateinit var editButtonListener: View.OnClickListener
    private lateinit var deleteButtonListener: View.OnClickListener

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
                .withColor(Color.DKGRAY, Color.TRANSPARENT)
                .bitmap())
        menu_amount.text = swosh.amount.toString() + " kr"
        menu_message.text = swosh.message
        menu_edit_button.setOnClickListener {
            editButtonListener.onClick(view)
            this.dismiss()
        }
        menu_share_button.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, swosh.url)
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)))
            this.dismiss()
        }
        menu_delete_button.setOnClickListener {
            deleteButtonListener.onClick(view)
            this.dismiss()

        }
    }

    fun setSwosh(swosh: Swosh) {
        this.swosh = swosh
    }

    fun setEditButtonListener(editButtonListener: View.OnClickListener) {
        this.editButtonListener = editButtonListener
    }

    fun setDeleteButtonListener(deleteButtonListener: View.OnClickListener) {
        this.deleteButtonListener = deleteButtonListener
    }
}