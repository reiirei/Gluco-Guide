package com.health.glucoguide.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.glucoguide.R
import com.health.glucoguide.adapter.WebLinkAdapter
import com.health.glucoguide.databinding.FragmentHomeBinding
import com.health.glucoguide.data.remote.response.WebLink
import com.health.glucoguide.ui.activity.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { userSession ->
            if(!userSession.isLogin) {
                val intent = Intent(requireContext(), OnBoardingActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                val name = userSession.name
                greeting(name)
            }
        }

        setupToolbar()
        setupShapeMessage()
        setupShapeBackground()
        setupAction()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
    }

    private fun setupAction() {
        binding.btnCheckNow.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment2_to_inputDataFragment)
        )
    }

    private fun setupRecyclerView() {
        val listNews = listOf(
            WebLink("How to Prevent Diabetes: Comprehensive Guide", "https://www.cdc.gov/diabetes/prevention/index.html", "https://www.cdc.gov/diabetes/images/prevention/diabetes-prevention.jpg", "Learn how to prevent diabetes with these comprehensive tips and guidelines provided by health experts."),
            WebLink("Diabetes Prevention: 5 Essential Tips for Taking Control", "https://www.mayoclinic.org/diseases-conditions/type-2-diabetes/in-depth/diabetes-prevention/art-20047639", "https://www.mayoclinic.org/-/media/kcms/gbs/patient-consumer/images/2018/11/21/19/56/diabetes-prevention-8col.jpg", "Five essential tips to help you take control of your health and prevent diabetes effectively."),
            WebLink("10 Effective Ways to Prevent Diabetes", "https://www.healthline.com/nutrition/prevent-diabetes", "https://post.healthline.com/wp-content/uploads/2020/08/diabetes-prevention-1200x628-facebook-1200x628.jpg", "Discover ten effective and practical ways to prevent diabetes and maintain a healthy lifestyle."),
            WebLink("Diabetes Prevention: Essential Lifestyle Tips", "https://www.webmd.com/diabetes/guide/preventing-diabetes", "https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/slideshows/diabetes_prevention_slideshow/650x350_diabetes_prevention_slideshow.jpg", "Essential lifestyle tips and changes you can adopt to help prevent diabetes and improve your overall health."),
            WebLink("How to Prevent Type 2 Diabetes: A Step-by-Step Guide", "https://www.diabetes.org/diabetes-risk/prevention", "https://www.diabetes.org/sites/default/files/styles/medium/public/2020-06/diabetes-prevention.jpg", "Follow this step-by-step guide to learn how to prevent type 2 diabetes and reduce your risk.")
        )

        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        val adapter = WebLinkAdapter { webLink ->
            val linkWeb = WebLink(webLink.title, webLink.url, webLink.imageUrl, webLink.description)
            val action = HomeFragmentDirections.actionHomeFragment2ToNewsFragment(linkWeb)
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.rvNews.adapter = adapter
        adapter.submitList(listNews)
    }

    private fun setupShapeMessage() {
        val cornerSizeMessage = resources.getDimension(R.dimen.corner_radius_message)

        val shapeAppearanceModelMessage = ShapeAppearanceModel.builder()
            .setTopRightCorner(RoundedCornerTreatment())
            .setTopRightCornerSize(cornerSizeMessage)
            .setBottomLeftCorner(RoundedCornerTreatment())
            .setBottomLeftCornerSize(cornerSizeMessage)
            .setBottomRightCorner(RoundedCornerTreatment())
            .setBottomRightCornerSize(cornerSizeMessage)
            .build()

        val shapeDrawableMessage = MaterialShapeDrawable(shapeAppearanceModelMessage)
        shapeDrawableMessage.fillColor = ContextCompat.getColorStateList(requireContext(), R.color.white)
        binding.cvMessage.background = shapeDrawableMessage
    }

    private fun setupShapeBackground() {
        val cornerSizeBackground = resources.getDimension(R.dimen.corner_radius_grey_background)
        val shapeAppearanceModelBackground = ShapeAppearanceModel.builder()
            .setBottomLeftCorner(RoundedCornerTreatment())
            .setBottomLeftCornerSize(cornerSizeBackground)
            .setBottomRightCorner(RoundedCornerTreatment())
            .setBottomRightCornerSize(cornerSizeBackground)
            .build()

        val shapeDrawableBackground = MaterialShapeDrawable(shapeAppearanceModelBackground)
        shapeDrawableBackground.fillColor = ContextCompat.getColorStateList(requireContext(), R.color.dark_blue)
        binding.materialCardView.background = shapeDrawableBackground
    }

    private fun greeting(name: String) {
        val greeting = getString(R.string.hi_gluco_friend, name)
        binding.tvGlucoGuide.text = greeting
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}