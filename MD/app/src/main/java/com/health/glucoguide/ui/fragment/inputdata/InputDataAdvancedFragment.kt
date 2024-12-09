package com.health.glucoguide.ui.fragment.inputdata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.snackbar.Snackbar
import com.health.glucoguide.R
import com.health.glucoguide.databinding.FragmentInputDataAdvancedBinding
import com.health.glucoguide.data.remote.request.UserData
import com.health.glucoguide.ui.activity.main.MainActivity

class InputDataAdvancedFragment : Fragment() {

    private var _binding: FragmentInputDataAdvancedBinding? = null
    private val binding get() = _binding!!
    private var isNetworkConnected: Boolean = false
    private lateinit var bodyMassIndex: EditText
    private lateinit var hemoglobinLevel: EditText
    private lateinit var glucoseLevel: EditText

    private val args: InputDataAdvancedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputDataAdvancedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as? MainActivity
        activity?.networkUtils?.observe(viewLifecycleOwner) { isConnected ->
            isNetworkConnected = isConnected
        }

        bodyMassIndex = binding.tiBodyMassIndex
        hemoglobinLevel = binding.tiHemoglobinA1cLevel
        glucoseLevel = binding.tiGlucoseLevel

        setupToolbar()

        setupShapeGreenBackground()
        setupShapeDoubleCheckCard()
        setupShapeReadyCheckCard()
        setupInputData()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupInputData() {
        val userData = args.userData

        binding.btnSubmit.setOnClickListener {
            if (!isNetworkConnected) {
                showSnackbar(getString(R.string.network_connection_error))
            } else {
                val bmi = bodyMassIndex.text.toString().trim()
                val hml = hemoglobinLevel.text.toString().trim()
                val gl = glucoseLevel.text.toString().trim()

                if (bmi.isEmpty() || hml.isEmpty() || gl.isEmpty()) {
                    showSnackbar(getString(R.string.error_empty_field), anchor = true)
                } else {
                    userData.bodyMassIndex = bmi.toDouble()
                    userData.hemoglobinLevel = hml.toDouble()
                    userData.glucoseLevel = gl.toInt()

                    sendUserData(userData)

                    clearInputData()
                }
            }
        }
    }

    private fun showSnackbar(errorMessage: String, color: Int = R.color.red, anchor: Boolean = false) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), color))
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            anchorView = if (anchor) binding.btnSubmit else null
            setAction(getString(R.string.ok)) {
                dismiss()
            }
            anchorView = if (anchor) requireActivity().findViewById(R.id.btn_submit) else null
        }.show()
    }

    private fun clearInputData() {
        bodyMassIndex.text?.clear()
        hemoglobinLevel.text?.clear()
        glucoseLevel.text?.clear()
    }

    private fun sendUserData(data: UserData) {
        val toResultFragment =
            InputDataAdvancedFragmentDirections.actionInputDataAdvancedFragmentToResultFragment(data)
        findNavController().navigate(toResultFragment)
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
        setupShape(binding.doubleCheckCard, R.dimen.corner_radius_grey_background, R.color.grey)
    }

    private fun setupShapeReadyCheckCard() {
        setupShape(binding.readyCard, R.dimen.corner_radius_grey_background, R.color.slightly_grey)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}