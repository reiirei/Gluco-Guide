package com.health.glucoguide.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.glucoguide.R
import com.health.glucoguide.databinding.FragmentResultBinding
import com.health.glucoguide.models.UserData

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userData = args.userData

        setupToolbar()
        setupShapeGreenBackground()
        setupShapeDoubleCheckCard()
        setupView(userData)
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivHome.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToHomeFragment2()
            findNavController().navigate(action)
        }
    }

    private fun setupShape(cardView: MaterialCardView, cornerSizeResId: Int, colorResId: Int) {
        val cornerSize = resources.getDimension(cornerSizeResId)
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setBottomLeftCorner(RoundedCornerTreatment())
            .setBottomLeftCornerSize(cornerSize)
            .setBottomRightCorner(RoundedCornerTreatment())
            .setBottomRightCornerSize(cornerSize)
            .build()

        cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), colorResId))
        cardView.shapeAppearanceModel = shapeAppearanceModel
        cardView.strokeWidth = 0
    }

    private fun setupShapeGreenBackground() {
        setupShape(binding.materialCardView, R.dimen.corner_radius_green_background, R.color.green_light)
    }

    private fun setupShapeDoubleCheckCard() {
        setupShape(binding.resultCard, R.dimen.corner_radius_grey_background, R.color.slightly_grey)
    }

    private fun setupView(userData: UserData) {
        binding.tvResultGender.text = userData.gender
        binding.tvResultAge.text = userData.age.toString()
        binding.tvResultSmokingHistory.text = userData.smokingHistory
        binding.tvResultHeartDisease.text = userData.heartDisease
        binding.tvResultHypertension.text = userData.hypertension
        binding.tvResultBodyMassIndex.text = userData.bodyMassIndex.toString()
        binding.tvResultHemoglobinA1cLevel.text = userData.hemoglobinLevel.toString()
        binding.tvResultBloodGlucoseLevel.text = userData.glucoseLevel.toString()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}