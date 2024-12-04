package com.health.glucoguide.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.health.glucoguide.R
import com.health.glucoguide.ui.activity.MainActivity
import com.health.glucoguide.ui.activity.onboarding.OnBoardingActivity
import com.health.glucoguide.ui.fragment.profile.ProfileViewModel

class BottomLogoutDialog(
    context: Context,
    private val viewModel: ProfileViewModel
) {
    private val bottomLogoutDialog: BottomSheetDialog = BottomSheetDialog(context)

    init {
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.bottom_logout_dialog,
            null as ViewGroup?, false
        )

        val btnLogout: AppCompatButton = dialogLayout.findViewById(R.id.btn_logout)
        val btnCancel: AppCompatButton = dialogLayout.findViewById(R.id.btn_cancel)

        btnLogout.setOnClickListener {
            viewModel.logout()
            dismissLogoutDialog()
            val intent = Intent(context, OnBoardingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
            if (context is MainActivity) {
                context.finish()
            }
        }

        btnCancel.setOnClickListener {
            bottomLogoutDialog.dismiss()
        }

        bottomLogoutDialog.apply {
            setContentView(dialogLayout)
            setCancelable(true)
        }
    }

    fun showLogoutDialog() {
        bottomLogoutDialog.show()
    }

    private fun dismissLogoutDialog() {
        bottomLogoutDialog.dismiss()
    }
}

