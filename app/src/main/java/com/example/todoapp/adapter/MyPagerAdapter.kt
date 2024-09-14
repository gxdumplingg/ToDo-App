package com.example.todoapp.adapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.example.todoapp.ui.home.HomeScreenFragment

class FragmentMainViewPager
    (fragmentManager: FragmentManager,
     lifecycle: Lifecycle,
     private val fragmentList: ArrayList<Fragment>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeScreenFragment()
//            1 -> CalendarFragment()
//            2 -> NoteFragment()
//            3 -> ArchivedFragment()
            else -> HomeScreenFragment()
        }
    }
}