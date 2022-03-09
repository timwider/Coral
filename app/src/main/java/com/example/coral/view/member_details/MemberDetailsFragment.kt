package com.example.coral.view.member_details

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coral.R
import com.example.coral.adapters.WorkoutsAdapter
import com.example.coral.data.Member
import com.example.coral.databinding.FragmentMemberDetailsBinding
import com.example.coral.utils.GlobalUtils
import com.example.coral.view.EditOrAddPaymentFragment
import com.example.coral.viewmodels.MainViewModel
import com.example.coral.viewmodels.MemberDetailsViewModel
import com.google.android.material.snackbar.Snackbar

private const val GET_MEMBER_KEY = "clicked_member"
private const val SEND_MEMBER_KEY = "member_to_edit"
private const val GET_EDITED_MEMBER_KEY = "edited_member"
private const val GET_EDITED_MEMBER_REQUEST_KEY = "edit_or_add_member"
private const val WHATSAPP_PACKAGE = "com.whatsapp"
private const val WHATSAPP_VIEW_DIALOG_URL = "https://api.whatsapp.com/send?phone="
private const val CALL_URI = "tel:"

class MemberDetailsFragment: Fragment(R.layout.fragment_member_details) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val memberDetailsViewModel: MemberDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentMemberDetailsBinding
    private val deleteMember = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMemberDetailsBinding.bind(view)
        val workoutsAdapter = WorkoutsAdapter()

        getMemberFromArgs()
        populateWorkoutsAdapter(workoutsAdapter)
        setupRecyclerView(workoutsAdapter)
        initObservers(workoutsAdapter)
        // This is data of member combined in a single row, it is set when new payment is added
        var dataRow = ""

        setClickListeners()
        setFragmentResultListener()

        super.onViewCreated(view, savedInstanceState)
    }

    // Saving data even if app is killed
    override fun onStop() {
        deleteOrUpdateMember(memberDetailsViewModel.formingMember.value!!)
        super.onStop()
    }

    override fun onDestroyView() {
        memberDetailsViewModel.deleteDateAfterSubmitted()
        super.onDestroyView()
    }

    private fun initObservers(workoutsAdapter: WorkoutsAdapter) {
        memberDetailsViewModel.formingMember.observe(viewLifecycleOwner) { member ->
            setDataFromCurrentPayment(member.currentPayment, member.name, member.daysLeft)
            populateWorkoutsAdapter(workoutsAdapter)
        }

        memberDetailsViewModel.selectedDate.observe(viewLifecycleOwner) { workoutDate ->
            onSelectedDateChanged(workoutDate, workoutsAdapter)
        }

        memberDetailsViewModel.memberNote.observe(viewLifecycleOwner) { memberNote ->
            binding.etMemberNote.setText(memberNote)
        }
    }

    private fun setFragmentResultListener() {
        activity?.supportFragmentManager?.setFragmentResultListener(
            GET_EDITED_MEMBER_REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            onFragmentResult(bundle)
        }
    }

    private fun setDataFromCurrentPayment(currentPayment: String?, name: String, daysLeft: Int) {

        binding.tvNameDetails.text = name
        binding.tvDaysLeftDetails.text = getString(R.string.details_et_days_left, daysLeft)
        binding.etMemberNote.setText(memberDetailsViewModel.memberNote.value)

        if (!currentPayment.isNullOrEmpty()) {
            val date = memberDetailsViewModel.getLastPaymentFromStringList(currentPayment).first
            val sum = memberDetailsViewModel.getLastPaymentFromStringList(currentPayment).second
            binding.tvLastPaymentDate.text = getString(R.string.details_tv_last_payment_date, date)
            binding.tvLastPaymentSum.text = getString(R.string.details_tv_last_payment_sum, sum)
        } else {
            binding.tvLastPaymentDate.text = getString(R.string.details_tv_last_payment_date_empty)
            binding.tvLastPaymentSum.text = getString(R.string.details_tv_last_payment_sum_empty)
        }
    }

    private fun onAddWorkoutClick() {
        if (memberDetailsViewModel.formingMember.value!!.daysLeft == 0) {
            Snackbar.make(requireView(), getString(R.string.details_message_no_days_left), Snackbar.LENGTH_SHORT).show()
        } else GlobalUtils().setupDatePicker(requireContext(), memberDetailsViewModel.datePickerListener())
    }

    private fun onCallClick() {
        val phone = memberDetailsViewModel.providePhoneForCalling()
        if (!phone.isNullOrEmpty()) {
            openPhoneDialScreen(phone)
        } else Snackbar.make(requireView(), getString(R.string.details_message_no_phone_provided), Snackbar.LENGTH_SHORT).show()
    }

    private fun openPhoneDialScreen(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(CALL_URI + phone)
        startActivity(intent)
    }

    private fun populateWorkoutsAdapter(adapter: WorkoutsAdapter) {
        memberDetailsViewModel.formingMember.value!!.workouts.let {
            adapter.submitList(it)
        }
    }

    private fun getMemberFromArgs() {
        arguments?.getParcelable<Member>(GET_MEMBER_KEY)?.let { member ->
            memberDetailsViewModel.getMemberFromArgs(member)
        }
    }

    private fun setupRecyclerView(workoutsAdapter: WorkoutsAdapter) {
        binding.rvWorkouts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workoutsAdapter
        }
    }

    private fun openWhatsappConversation(whatsappPhone: String) {
        val intent = Intent(ACTION_VIEW).apply {
            `package` = WHATSAPP_PACKAGE
            data = Uri.parse(WHATSAPP_VIEW_DIALOG_URL + whatsappPhone)
        }
        startActivity(intent)
    }

    private fun onWhatsappClick() {
        val phone = memberDetailsViewModel.provideMemberPhoneForWhatsapp()
        if (phone != null) {
            openWhatsappConversation(phone)
        } else Snackbar.make(requireView(), getString(R.string.details_message_no_phone_provided), Snackbar.LENGTH_SHORT).show()
    }

    private fun onSelectedDateChanged(workoutDate: String?, workoutsAdapter: WorkoutsAdapter) {
        if (!workoutDate.isNullOrEmpty()
            && workoutDate != memberDetailsViewModel.formingMember.value!!.workouts?.last()) {
            memberDetailsViewModel.updateMemberOnDateSet()
            binding.tvDaysLeftDetails.text = getString(R.string.details_et_days_left,
                memberDetailsViewModel.formingMember.value!!.daysLeft)

            workoutsAdapter.submitList(memberDetailsViewModel.formingMember.value!!.workouts)
            workoutsAdapter.notifyDataSetChanged()
        }
    }

    private fun onAddPaymentClicked() {
        activity?.supportFragmentManager?.commit {
            val editOrAddPaymentFragment = EditOrAddPaymentFragment()
            val bundle = Bundle()
            bundle.putParcelable(SEND_MEMBER_KEY, memberDetailsViewModel.formingMember.value!!)
            editOrAddPaymentFragment.arguments = bundle
            replace(R.id.fragment_container, editOrAddPaymentFragment)
            addToBackStack(null)
        }
    }

    private fun onFragmentResult(bundle: Bundle) {
        val editedMember: Member? = bundle.getParcelable(GET_EDITED_MEMBER_KEY)
        memberDetailsViewModel.onFragmentResult(editedMember)
    }

    private fun setClickListeners() {
        binding.apply {
            btnAddWorkout.setOnClickListener { onAddWorkoutClick() }
            btnCall.setOnClickListener { onCallClick() }
            btnWhatsapp.setOnClickListener { onWhatsappClick() }
            btnAddPaymentDetails.setOnClickListener { onAddPaymentClicked() }
        }
    }

    private fun deleteOrUpdateMember(member: Member) {
        memberDetailsViewModel.submitNote(binding.etMemberNote.text.toString())
        if (deleteMember) {
            mainViewModel.deleteMember(member)
        } else mainViewModel.updateMember(member)
    }
}