package dima_merzlov.com.test_task_with_fragment.viewpager2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dima_merzlov.com.test_task_with_fragment.CounterFragment

class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    list: MutableList<CounterFragment>
) : FragmentStateAdapter(fm, lifecycle) {
    //private val fragmentList: ArrayList<Fragment> = list
    override fun getItemCount(): Int {
        return 0
    }

    override fun createFragment(position: Int): Fragment {
        return Fragment()
    }

}