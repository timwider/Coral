package com.example.coral.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coral.view.member_details.MemberDetailsHistoryFragment
import com.example.coral.view.member_details.MemberDetailsFragment

class MemberHistoryTabsAdapter(
    fragment: Fragment,
    private var clickedMember: Bundle?
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                val fragment = MemberDetailsFragment()
                fragment.arguments = clickedMember
                fragment
            }
            1 -> MemberDetailsHistoryFragment()
            else -> MemberDetailsFragment()
        }
    }
}