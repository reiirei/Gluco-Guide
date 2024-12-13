package com.health.glucoguide.ui.fragment.inputdata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.snackbar.Snackbar
import com.health.glucoguide.R
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.databinding.FragmentInputDataAdvancedBinding
import com.health.glucoguide.data.remote.request.UserDataPredict
import com.health.glucoguide.util.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputDataAdvancedFragment : Fragment() {

    private var _binding: FragmentInputDataAdvancedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InputDataAdvancedViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(requireContext())
    }
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

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            setupInputData(user.name)
        }

        bodyMassIndex = binding.tiBodyMassIndex
        hemoglobinLevel = binding.tiHemoglobinA1cLevel
        glucoseLevel = binding.tiGlucoseLevel

        setupToolbar()

        setupShapeGreenBackground()
        setupShapeDoubleCheckCard()
        setupShapeReadyCheckCard()

    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupInputData(name: String) {
        val userData = args.userData

        binding.btnSubmit.setOnClickListener {
            val bmi = bodyMassIndex.text.toString().trim()
            val hml = hemoglobinLevel.text.toString().trim()
            val gl = glucoseLevel.text.toString().trim()

            if (bmi.isEmpty() || hml.isEmpty() || gl.isEmpty()) {
                showSnackbar(getString(R.string.error_empty_field), anchor = true)
            } else {
                userData.name = name
                userData.bmi = bmi.toFloat()
                userData.HbA1c_level = hml.toFloat()
                userData.blood_glucose_level = gl.toInt()

                viewModel.sendUserData(userData).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            val data = UserDataPredict(
                                name = result.data.input.name,
                                age = result.data.input.age,
                                hypertension = result.data.input.hypertension,
                                heartDisease = result.data.input.heart_disease,
                                bodyMassIndex = result.data.input.bmi,
                                hemoglobinLevel = result.data.input.HbA1c_level,
                                glucoseLevel = result.data.input.blood_glucose_level,
                                gender = result.data.input.gender_encoded,
                                smokingHistory = result.data.input.smoking_history_encoded,
                                description = result.data.description
                            )
                            goToResultFragment(data)
                        }
                        is ResultState.Error -> {
                            progressDialog.hideLoading()
                            showSnackbar(result.error)
                        }
                    }
                }

                clearInputData()
            }
        }
    }

    private fun clearInputData() {
        bodyMassIndex.text?.clear()
        hemoglobinLevel.text?.clear()
        glucoseLevel.text?.clear()
    }



    private fun goToResultFragment(data: UserDataPredict) {
        val toResultFragment =
            InputDataAdvancedFragmentDirections.actionInputDataAdvancedFragmentToResultFragment(data)
        findNavController().navigate(toResultFragment)
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
        }.show()
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