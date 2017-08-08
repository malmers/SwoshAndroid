package me.swosh.android.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.swosh.android.R

/**
 * Created by mikael on 2017-08-06.
 */
class OptionsMenuFragment : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.long_click_menu, container, false)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO init UI

    }
}