package com.example.coral.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.coral.R
import com.example.coral.data.Member
import com.example.coral.databinding.AddNewMemberFragmentBinding
import com.example.coral.utils.GlobalUtils
import com.example.coral.viewmodels.AddNewMemberViewModel
import com.example.coral.viewmodels.MainViewModel

class AddNewMemberFragment: Fragment(R.layout.add_new_member_fragment) {

    private val model: AddNewMemberViewModel by viewModels()
    private val parentModel: MainViewModel by activityViewModels()
    private lateinit var binding: AddNewMemberFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = AddNewMemberFragmentBinding.bind(view)

        setClickListeners()

        // Date selected from DatePickerDialog
        model.selectedDate.observe(viewLifecycleOwner) { date ->
            if (!date.isNullOrEmpty()) {
                changeLayoutOnDatePicked()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createAndSaveMember() {
        val member = Member(
            id = 0,
            name = binding.etName.text.toString(),
            phone = binding.etPhoneNumber.text.toString(),
            daysLeft = binding.etDaysLeft.text.toString().toInt(),
            currentPayment = "${model.selectedDate.value!!} ${binding.etFirstPaymentSum.text.toString()}",
            workouts = null,
            memberHistory = null,
            note = null
        )

        parentModel.insertMember(member)
        parentFragmentManager.commit { replace(R.id.fragment_container, HomeFragment()) }
        parentFragmentManager.popBackStack()
    }

    private fun setClickListeners() {
        binding.btnAddFirstPayment.setOnClickListener {
            GlobalUtils().setupDatePicker(requireContext(), model.onDateSetListener())
        }

        binding.btnSave.setOnClickListener {
            if (!model.selectedDate.value.isNullOrEmpty()) {
                createAndSaveMember()
            }
        }
    }

    private fun changeLayoutOnDatePicked() {
        binding.textFieldFirstPaymentSum.isVisible = true
        binding.btnAddFirstPayment.isVisible = false
        binding.textFieldFirstPaymentSum.helperText = model.createHelperText()
    }
}