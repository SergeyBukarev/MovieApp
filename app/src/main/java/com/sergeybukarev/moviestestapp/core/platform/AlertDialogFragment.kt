package com.sergeybukarev.moviestestapp.core.platform

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sergeybukarev.moviestestapp.presentation.helpers.args

class AlertDialogFragment : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    private var title: CharSequence? by args.charSequence()
    private var message: CharSequence? by args.charSequence()

    companion object {
        operator fun invoke(title: CharSequence?, message: CharSequence?) = AlertDialogFragment().apply {
            this.title = title
            this.message = message
        }
    }
}
