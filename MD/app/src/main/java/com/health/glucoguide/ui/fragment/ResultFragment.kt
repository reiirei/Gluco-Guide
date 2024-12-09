package com.health.glucoguide.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.glucoguide.R
import com.health.glucoguide.adapter.WebLinkAdapter
import com.health.glucoguide.databinding.FragmentResultBinding
import com.health.glucoguide.data.remote.request.UserData
import com.health.glucoguide.data.remote.response.WebLink

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
        setupRecyclerView()
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
        setupShape(binding.materialCardView, R.dimen.corner_radius_green_background, R.color.light_army)
    }

    private fun setupShapeDoubleCheckCard() {
        setupShape(binding.resultCard, R.dimen.corner_radius_grey_background, R.color.grey)
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

    private fun setupRecyclerView() {
        val listNews = listOf(
            WebLink("10 Effective Ways to Prevent Diabetes", "https://www.healthline.com/nutrition/prevent-diabetes", "https://post.healthline.com/wp-content/uploads/2020/08/diabetes-prevention-1200x628-facebook-1200x628.jpg", "Discover ten effective and practical ways to prevent diabetes and maintain a healthy lifestyle."),
            WebLink("Diabetes Prevention: Essential Lifestyle Tips", "https://www.webmd.com/diabetes/guide/preventing-diabetes", "https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/slideshows/diabetes_prevention_slideshow/650x350_diabetes_prevention_slideshow.jpg", "Essential lifestyle tips and changes you can adopt to help prevent diabetes and improve your overall health."),
            WebLink("How to Prevent Type 2 Diabetes: A Step-by-Step Guide", "https://www.diabetes.org/diabetes-risk/prevention", "https://www.diabetes.org/sites/default/files/styles/medium/public/2020-06/diabetes-prevention.jpg", "Follow this step-by-step guide to learn how to prevent type 2 diabetes and reduce your risk.")
        )

        binding.rvSuggestion.layoutManager = LinearLayoutManager(requireContext())
        val adapter = WebLinkAdapter { webLink ->
            val linkWeb = WebLink(webLink.title, webLink.url, webLink.imageUrl, webLink.description)
            val action = ResultFragmentDirections.actionResultFragmentToNewsFragment(linkWeb)
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.rvSuggestion.adapter = adapter
        adapter.submitList(listNews)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}