package com.example.todoapp.ui.dialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.todoapp.R

class SuccessDialog(context: Context) {
    private val dialog: Dialog = Dialog(context)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.success_dialog, null)
        dialog.setContentView(view)

        val btnClose: Button = view.findViewById(R.id.btnClose)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }
    }

    var message: String
        get() = (dialog.findViewById<TextView>(R.id.tvMessage)).text.toString()
        set(value) {
            dialog.findViewById<TextView>(R.id.tvMessage).text = value
        }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}
