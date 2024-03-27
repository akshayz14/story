package com.aksstore.storily.model

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.aksstore.storily.R
import com.aksstore.storily.databinding.CustomDialogBinding

class CustomDialog(context: Context, private val listener: OnDialogButtonClickListener) : Dialog(context) {

    private lateinit var editText: EditText
    private lateinit var dialogButton: Button
    private lateinit var binding: CustomDialogBinding
    private lateinit var dialogTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)

        dialogButton = binding.dialogButton

        dialogButton.setOnClickListener {
            val text = editText.text.toString()
            listener.onButtonClick(text)
            dismiss()
        }
    }

    interface OnDialogButtonClickListener {
        fun onButtonClick(text: String)
    }
}