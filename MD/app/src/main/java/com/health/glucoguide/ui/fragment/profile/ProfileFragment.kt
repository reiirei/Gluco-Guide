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
import com.health.glucoguide.data.remote.response.UserSession
import com.health.glucoguide.databinding.FragmentProfileBinding
import com.health.glucoguide.util.BottomChoseLanguageDialog
import com.health.glucoguide.util.BottomLogoutDialog
import com.health.glucoguide.util.NetworkUtils
import com.health.glucoguide.util.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private val progressDialog by lazy { ProgressDialog(requireContext()) }
    private val bottomLogoutDialog by lazy { BottomLogoutDialog(requireContext(), viewModel) }
    private val bottomChoseLanguageDialog by lazy { BottomChoseLanguageDialog(requireContext(), viewModel) }
    private val networkUtils: NetworkUtils by lazy { NetworkUtils(requireContext()) }
    private lateinit var userSession: UserSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNetwork()

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            val token: String = user.token.toString()
            val name: String = user.name
            updateUsernameUI(name)

            viewModel.getUserData(token)
            userSession = UserSession(name, token, user.isLogin)
            setupAction(userSession)
            setupObservers()
        }

        setupToolbar()
        setupShapeMessage()
    }

    private fun observeNetwork() {
        networkUtils.observe(viewLifecycleOwner) { isNetworkAvailable ->
            if (!isNetworkAvailable) {
                showSnackbar(getString(R.string.network_connection_error))
            }
        }
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
            showSnackbar(errorMessage)
        }
    }

    private fun showSnackbar(errorMessage: String, color: Int = R.color.red) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), color))
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            anchorView = requireActivity().findViewById(R.id.bottom_navigation)
            setAction(getString(R.string.ok)) {
                dismiss()
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

    private fun setupAction(userSession: UserSession) {
        binding.cvShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = getString(R.string.text_plain)
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.play_store) + requireContext().packageName)
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }

        binding.cvLanguage.setOnClickListener {
            if (networkUtils.value == true) {
                bottomChoseLanguageDialog.showDialog()
            } else {
                showSnackbar(requireContext().getString(R.string.network_connection_error))
            }
        }

        binding.cvLogout.setOnClickListener {
            if (networkUtils.value == true) {
                bottomLogoutDialog.showLogoutDialog()
            } else {
                showSnackbar(requireContext().getString(R.string.network_connection_error))
            }
        }


        binding.ivEdit.setOnClickListener{
            val navigateToEditProfile = ProfileFragmentDirections
                .actionProfileFragmentToEditProfileFragment(userSession)
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