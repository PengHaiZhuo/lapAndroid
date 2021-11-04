package com.phz.dev.feature.practice.dialog.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.phz.dev.R

/**
 * @author phz
 * @description
 */
class DialogSampleFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Dialog Fragment").setMessage("Msg Msg Msg\n What a funny day")
                .setPositiveButton(
                    R.string.confirm
                ) { dialog, _ ->//dialog, id
                    dialog.dismiss()
                }
                .setNegativeButton(
                    R.string.cancel
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }
}