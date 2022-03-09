package com.example.coral.view.member_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coral.R
import com.example.coral.adapters.MembersHistoryAdapter
import com.example.coral.databinding.FragmentMemberDetailsHistoryBinding
import com.example.coral.viewmodels.MainViewModel
import com.example.coral.viewmodels.MemberDetailsHistoryViewModel
import com.example.coral.viewmodels.MemberDetailsViewModel

class MemberDetailsHistoryFragment: Fragment(R.layout.fragment_member_details_history) {

    private val memberDetailsViewModel: MemberDetailsViewModel by activityViewModels()
    private lateinit var binding: FragmentMemberDetailsHistoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMemberDetailsHistoryBinding.bind(view)
        val memberHistoryAdapter = MembersHistoryAdapter()

        binding.rvMemberHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = memberHistoryAdapter
        }

        memberDetailsViewModel.formingMember.observe(viewLifecycleOwner) {
            if (!it.memberHistory.isNullOrEmpty()) {
                memberHistoryAdapter.submitList(it.memberHistory)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}