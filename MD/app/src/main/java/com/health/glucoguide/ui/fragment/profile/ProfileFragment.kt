package com.health.glucoguide.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.health.glucoguide.R
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.health.glucoguide.databinding.FragmentProfileBinding
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
            val name: String = user.name

            updateUsernameUI(name)
            viewModel.getUserData(token)
        }

        setupToolbar()
        setupShapeMessage()
        setupObservers()
        setupAction()
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        viewModel.username.observe(viewLifecycleOwner) { username ->
            updateUsernameUI(username)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                showSnackbar(errorMessage)
            }
        }
    }

    private fun showSnackbar(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.black))
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            anchorView = requireActivity().findViewById(R.id.bottom_navigation)
            setAction("Retry") {
                viewModel.getUserData(viewModel.getSession().value?.token.toString())
            }
        }.show()
    }

    private fun showLoading() {
        progressDialog.showLoading()
    }

    private fun hideLoading() {
        progressDialog.hideLoading()
    }

    private fun updateUsernameUI(username: String) {
        val greeting = getString(R.string.hi_gluco_friend, username)
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
            if (viewModel.errorMessage.value.isNullOrEmpty()) {
                bottomLogoutDialog.showLogoutDialog()
            } else {
                bottomLogoutDialog.dismissLogoutDialog()
                showSnackbar(requireContext().getString(R.string.network_connection_error))
            }
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