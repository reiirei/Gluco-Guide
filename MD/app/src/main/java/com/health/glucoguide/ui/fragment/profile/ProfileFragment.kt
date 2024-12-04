package com.health.glucoguide.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.glucoguide.R
import androidx.navigation.fragment.findNavController
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.databinding.FragmentProfileBinding
import com.health.glucoguide.models.UserProfileResponse
import com.health.glucoguide.util.BottomLogoutDialog
import com.health.glucoguide.util.ProgressDialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val progressDialog by lazy { ProgressDialogUtil(requireContext()) }
    private val viewModel: ProfileViewModel by viewModels()
    private val bottomLogoutDialog by lazy { BottomLogoutDialog(requireContext(), viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            val token: String = user.token.toString()
            getUserData(token)
        }

        setupToolbar()
        setupShapeMessage()
        setupAction()
    }

    private fun getUserData(token: String) {
        viewModel.getUserData(token)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ResultState.Loading -> {
                        progressDialog.showLoading()
                    }

                    is ResultState.Success -> {
                        progressDialog.hideLoading()
                        greeting(result.data)
                    }

                    is ResultState.Error -> {
                        progressDialog.hideLoading()
                        val errorMessage = result.error
                        showToast(errorMessage)
                    }
                }
            }
    }

    private fun showToast(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun greeting(response: UserProfileResponse) {
        val greeting = getString(R.string.hi_gluco_friend, response.user?.name)
        binding.tvGlucoGuide.text = greeting
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
    }

    private fun setupAction() {
        binding.cvShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = getString(R.string.text_plain)
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.play_store) + requireContext().packageName)
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }

        binding.cvLogout.setOnClickListener {
            bottomLogoutDialog.showLogoutDialog()
        }

        binding.ivEdit.setOnClickListener{
            val navigateToEditProfile = ProfileFragmentDirections
                .actionProfileFragmentToEditProfileFragment()
            findNavController().navigate(navigateToEditProfile)
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}