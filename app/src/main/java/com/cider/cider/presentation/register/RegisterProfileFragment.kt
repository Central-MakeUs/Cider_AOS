package com.cider.cider.presentation.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.cider.cider.R
import com.cider.cider.databinding.FragmentRegisterProfileBinding
import com.cider.cider.domain.type.Birth
import com.cider.cider.domain.type.Gender
import com.cider.cider.domain.type.RegisterType
import com.cider.cider.presentation.viewmodel.LoginViewModel
import com.cider.cider.utils.binding.BindingFragmentNoNavi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RegisterProfileFragment
    :BindingFragmentNoNavi<FragmentRegisterProfileBinding>(R.layout.fragment_register_profile) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.changeRegisterState(RegisterType.INFORMATION_INPUT2)

        setButton()
        setObserver()
        setBirth()
    }

    private fun setButton() {
        binding.btnProfileMale.setOnClickListener {
            viewModel.changeGender(Gender.MALE)
        }

        binding.btnProfileFemale.setOnClickListener {
            viewModel.changeGender(Gender.FEMALE)
        }
    }

    private fun setObserver () {
        viewModel.genderState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch (Dispatchers.Main) {
                if (it == Gender.MALE) {
                    binding.btnProfileMale.isSelected = true
                    binding.btnProfileFemale.isSelected = false
                } else if (it == Gender.FEMALE){
                    binding.btnProfileMale.isSelected = false
                    binding.btnProfileFemale.isSelected = true
                }
            }
        }
        viewModel.birth.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main){
                if (it.year != 0) {
                    binding.btnProfileBirth.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
                    if (it.hasPassed14Years()) {
                        binding.btnProfileBirth.setBackgroundResource(R.drawable.shape_edittext_active)
                        binding.tvCheckBirth.visibility = View.GONE
                    } else {
                        binding.btnProfileBirth.setBackgroundResource(R.drawable.shape_edittext_error)
                        binding.tvCheckBirth.visibility = View.VISIBLE
                    }
                } else {
                    binding.btnProfileBirth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_4))
                }
            }
        }
    }

    private fun setBirth() {
        binding.btnProfileBirth.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.set( if ((viewModel.birth.value?.year ?: cal.get(Calendar.YEAR)) <= 1900) cal.get(Calendar.YEAR) else viewModel.birth.value?.year?:0,
                viewModel.birth.value?.month?:cal.get(Calendar.MONTH),
                viewModel.birth.value?.day?:cal.get(Calendar.DAY_OF_MONTH))

            Log.d("TEST","${cal.get(Calendar.YEAR)} / ${cal.get(Calendar.MONTH)} / ${cal.get(Calendar.DAY_OF_MONTH)}")
            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                viewModel.changeBirth(Birth(year, month, day))
            }
            val dialog = DatePickerDialog(
                requireContext(),
                R.style.SpinnerDatePickerDialogTheme,
                data,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
    }
}