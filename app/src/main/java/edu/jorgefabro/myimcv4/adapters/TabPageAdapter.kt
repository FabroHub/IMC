package edu.jorgefabro.myimcv4.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.jorgefabro.myimcv4.fragments.HistoFragment
import edu.jorgefabro.myimcv4.fragments.ImcFragment

class TabPageAdapter(activity: FragmentActivity, private val tabCount: Int) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ImcFragment()
            1 -> HistoFragment()
            else -> ImcFragment()
        }
    }
}