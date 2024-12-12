package com.health.glucoguide.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.glucoguide.R
import com.health.glucoguide.ui.activity.main.MainActivity
import com.health.glucoguide.ui.activity.onboarding.OnBoardingActivity
import com.health.glucoguide.ui.fragment.profile.ProfileViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

class BottomLogoutDialog(
    @ApplicationContext private val context: Context,
    private val viewModel: ProfileViewModel
) {
    private val bottomLogoutDialog: BottomSheetDialog = BottomSheetDialog(context)

    init {
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.bottom_logout_dialog,
            null as ViewGroup?, false
        )

        val card = dialogLayout.findViewById<MaterialCardView>(R.id.card_bottom_logout)
        setupShape(card, R.dimen.corner_radius_grey_background, R.color.grey)

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

    private fun setupShape(cardView: MaterialCardView, cornerSizeResId: Int, colorResId: Int) {
        val cornerSize = context.resources.getDimension(cornerSizeResId)
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setTopLeftCornerSize(cornerSize)
            .setTopRightCorner(RoundedCornerTreatment())
            .setTopRightCornerSize(cornerSize)
            .build()

        cardView.setCardBackgroundColor(ContextCompat.getColor(context, colorResId))
        cardView.shapeAppearanceModel = shapeAppearanceModel
        cardView.strokeWidth = 0
    }

    fun showLogoutDialog() {
        bottomLogoutDialog.show()
    }

    private fun dismissLogoutDialog() {
        bottomLogoutDialog.dismiss()
    }
}

