package com.example.coral.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coral.R
import com.example.coral.data.Member
import com.example.coral.databinding.FragmentEditOrAddPaymentBinding
import com.example.coral.utils.GlobalUtils
import com.example.coral.viewmodels.EditOrAddPaymentViewModel

private const val GET_MEMBER_KEY = "member_to_edit"
private const val SEND_MEMBER_KEY = "edited_member"
private const val FRAGMENT_RESULT_REQUEST_KEY = "edit_or_add_member"

class EditOrAddPaymentFragment: Fragment(R.layout.fragment_edit_or_add_payment) {

    private lateinit var binding: FragmentEditOrAddPaymentBinding
    private val model: EditOrAddPaymentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val memberToEdit: Member = arguments?.getParcelable(GET_MEMBER_KEY)!!
        arguments?.clear()

        binding = FragmentEditOrAddPaymentBinding.bind(view)

        model.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            if (!selectedDate.isNullOrEmpty()) {
                changeLayoutOnDatePicked(memberToEdit.daysLeft)
            }
        }

        setupEditText(memberToEdit.name, memberToEdit.daysLeft, memberToEdit.phone)
        setClickListeners(memberToEdit)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupEditText(name: String, daysLeft: Int, phone: String?) {
        binding.etName.setText(name)
        binding.etDaysLeft.setText(daysLeft.toString())
        binding.etPhoneNumber.setText(phone)
    }

    private fun createAndSendMember(memberToEdit: Member, isPaymentAdded: Boolean) {

        val newDaysLeft = binding.etDaysLeft.text.toString().toInt()

        val newPayment = model.provideNewPayment(
            binding.etFirstPaymentSum.text.toString(),
            memberToEdit.currentPayment!!)

        val newMember = Member (
            id = memberToEdit.id,
            name = binding.etName.text.toString(),
            phone = binding.etPhoneNumber.text.toString(),
            daysLeft = newDaysLeft,
            currentPayment = newPayment,
            workouts = null,
            memberHistory = memberToEdit.memberHistory,
            note = memberToEdit.note
        )
        newMember.workouts = model.provideWorkouts(newPayment, memberToEdit.currentPayment!!, memberToEdit.workouts)

        val bundle = Bundle()
        bundle.putParcelable(SEND_MEMBER_KEY, newMember)
        activity?.supportFragmentManager?.setFragmentResult(FRAGMENT_RESULT_REQUEST_KEY, bundle)
        parentFragmentManager.popBackStack()
    }

    private fun changeLayoutOnDatePicked(daysLeft: Int) {
        binding.apply {
            textFieldFirstPaymentSum.isVisible = true
            btnAddPayment.isVisible = false
            textFieldFirstPaymentSum.helperText = getString(R.string.et_helper_text_payment_date, model.selectedDate.value)
            etDaysLeft.setText(model.setDaysFromPayment(daysLeft))
        }
    }

    private fun setClickListeners(memberToEdit: Member) {
        binding.btnAddPayment.setOnClickListener {
            GlobalUtils().setupDatePicker(requireContext(), model.onDateSelectedListener()) }

        binding.btnSave.setOnClickListener { createAndSendMember(memberToEdit, model.isPaymentAdded.value!!) }
    }
}