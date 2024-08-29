package com.aksstore.storily.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.aksstore.storily.R
import com.aksstore.storily.utils.AppConstants
import com.bumptech.glide.Glide

class FullScreenImageDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_full_image)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.setCanceledOnTouchOutside(true)
        val fullImageView = dialog.findViewById<ImageView>(R.id.fullImageView)
        Glide.with(requireContext())
            .load(arguments?.getString(AppConstants.IMAGE_URL))
            .into(fullImageView)

        return dialog
    }
}