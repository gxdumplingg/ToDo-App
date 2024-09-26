package com.example.todoapp.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.example.todoapp.R

class CustomDialog(context: Context) {
    private val dialog: Dialog = Dialog(context)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.confirmation_dialog, null)
        dialog.setContentView(view)

        val btnConfirm: Button = view.findViewById(R.id.btn_confirm)
        val btnCancel: Button = view.findViewById(R.id.btn_cancel)

        btnConfirm.setOnClickListener {
            onConfirmClickListener?.invoke()
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    var message: String
        get() = (dialog.findViewById<TextView>(R.id.dialog_message)).text.toString()
        set(value) {
            dialog.findViewById<TextView>(R.id.dialog_message).text = value
        }

    var onConfirmClickListener: (() -> Unit)? = null

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}