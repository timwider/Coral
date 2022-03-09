package com.example.coral.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coral.adapters.MembersAdapter
import com.example.coral.R
import com.example.coral.data.Member
import com.example.coral.databinding.FragmentHomeBinding
import com.example.coral.view.member_details.MemberDetailsHolderFragment
import com.example.coral.viewmodels.MainViewModel

private const val ADD_MEMBER_FRAGMENT_TAG = "add_member_fragment"
private const val MEMBER_DETAILS_HOLDER_FRAGMENT_TAG = "member_details_holder_fragment"
private const val PUT_MEMBER_TAG = "clicked_member"

class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentHomeBinding.bind(view)

        val adapter = MembersAdapter { navigateToMemberDetailsFragment(it) }

        binding.rvMembers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMembers.adapter = adapter

        mainViewModel.getAllMembers()
        mainViewModel.allMembers.observe(viewLifecycleOwner) { adapter.submitList(it) }

        binding.fabAddMember.setOnClickListener { navigateToAddMemberFragment() }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigateToMemberDetailsFragment (member: Member) {
        val memberDetailsHolderFragment = MemberDetailsHolderFragment()
        parentFragmentManager.beginTransaction().apply {
            val bundle = Bundle()
            bundle.putParcelable(PUT_MEMBER_TAG, member)
            memberDetailsHolderFragment.arguments = bundle
            replace(R.id.fragment_container, memberDetailsHolderFragment, MEMBER_DETAILS_HOLDER_FRAGMENT_TAG)
            addToBackStack(null)
            commit()
        }
    }

    private fun navigateToAddMemberFragment() {
        val addMemberFragment = AddNewMemberFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, addMemberFragment, ADD_MEMBER_FRAGMENT_TAG)
            .addToBackStack(ADD_MEMBER_FRAGMENT_TAG)
            .commit()
    }
}

