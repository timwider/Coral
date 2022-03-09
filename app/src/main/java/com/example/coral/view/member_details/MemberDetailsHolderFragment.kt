package com.example.coral.view.member_details

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.coral.R
import com.example.coral.adapters.MemberHistoryTabsAdapter
import com.example.coral.data.Member
import com.example.coral.databinding.DemoHolderFragmentBinding
import com.example.coral.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val GET_MEMBER_KEY = "clicked_member"

class MemberDetailsHolderFragment: Fragment(R.layout.demo_holder_fragment) {

    private lateinit var adapter: MemberHistoryTabsAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: DemoHolderFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = DemoHolderFragmentBinding.bind(view)

        adapter = MemberHistoryTabsAdapter(this, arguments)
        binding.viewPager2.adapter = adapter
        setupToolbar()
        setupTabMediator()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun menuItemClickListener() = Toolbar.OnMenuItemClickListener { createAlertDialog()?.show(); true }

    private fun setupToolbar() {
        binding.toolbarHome.apply {
            inflateMenu(R.menu.menu_member_details)
            setOnMenuItemClickListener(menuItemClickListener())
        }
    }

    private fun setupTabMediator() {
        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.viewPager2) { tab, position ->
            when(position) {
                0 -> tab.text =  getString(R.string.tab_first_text)
                1 -> tab.text = getString(R.string.tab_second_text)
            }
        }.attach()
    }

    private fun deleteMemberAndExit() {
        val currentMember: Member? = arguments?.getParcelable(GET_MEMBER_KEY)
        mainViewModel.deleteMember(currentMember!!)
        parentFragmentManager.popBackStack()
    }

    private fun createAlertDialog() =
        activity?.let {
            AlertDialog.Builder(it)
        }?.apply {
            setTitle(getString(R.string.dialog_title_delete_member))
            setMessage(getString(R.string.dialog_message_delete_member))
            setPositiveButton(getString(R.string.dialog_positive_button_text), dialogClickListener())
            setNegativeButton(getString(R.string.dialog_negative_button_text), dialogClickListener())
        }

    private fun dialogClickListener() =
        DialogInterface.OnClickListener { dialogInterface, i ->
            when (i) {
                DialogInterface.BUTTON_POSITIVE -> deleteMemberAndExit()
                DialogInterface.BUTTON_NEGATIVE -> dialogInterface.dismiss()
            }
        }
}