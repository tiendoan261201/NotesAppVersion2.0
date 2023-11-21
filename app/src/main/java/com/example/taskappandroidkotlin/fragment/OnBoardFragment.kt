package com.example.taskappandroidkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.taskappandroidkotlin.R
import com.example.taskappandroidkotlin.databinding.FragmentOnBoardBinding
import com.example.taskappandroidkotlin.onboarding.OnBoardAdapter
import com.example.taskappandroidkotlin.preference.NotePref
import com.example.taskappandroidkotlin.viewmodel.OnBoardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OnBoardFragment : BaseFragment<FragmentOnBoardBinding>(), OnboardButtonListener {

    private val viewModel: OnBoardViewModel by viewModels()
    private lateinit var onBoardAdapter: OnBoardAdapter
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback


    override fun initView() {
        binding.onboardingFragment = this
        viewModel.boardingLiveData.observe(viewLifecycleOwner){
            it.let {
                onBoardAdapter = OnBoardAdapter(it)
                binding.viewPagerOnBoard.adapter = onBoardAdapter
                binding.viewPagerOnBoard.registerOnPageChangeCallback(pageChangeListener)
                indicators()
                setCurrentIndicator(0)
            }
        }
        pageChangeListener = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                binding.preButtonVisibility = position != 0
                binding.nextButtonText = if(position == onBoardAdapter.itemCount-1) resources.getString(R.string.finish)
                else resources.getString(R.string.next)
            }
        }
    }

    override fun initData() {

    }

    override fun initListener() {
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoardBinding {
        return FragmentOnBoardBinding.inflate(inflater, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            OnBoardFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun prevClickListener() {
        if (binding.viewPagerOnBoard.currentItem > 0) binding.viewPagerOnBoard.currentItem -= 1
    }

    override fun nextClickListener() {
        if (binding.viewPagerOnBoard.currentItem + 1 < onBoardAdapter.itemCount) binding.viewPagerOnBoard.currentItem += 1
        else nextScreen()
    }

    fun nextScreen() {
        val userPref = NotePref(requireContext())
        CoroutineScope(Dispatchers.Main).launch {

            userPref.setFirstUsage(false)
           replaceFragment(HomeFragment.newInstance(),false)
        }
    }

    private fun indicators() {
        val indicator = arrayOfNulls<ImageView>(onBoardAdapter.itemCount)
        val layourParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layourParams.setMargins(8, 0, 8, 0)

        for (i in indicator.indices) {
            indicator[i] = ImageView(requireContext().applicationContext)
            indicator[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.bg_indicator_inactive
                    )
                )
                it.layoutParams = layourParams
                binding.indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = binding.indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.bg_indicator_active
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.bg_indicator_inactive
                    )
                )
            }
        }
    }
    private fun replaceFragment(fragment: Fragment, isTransaction: Boolean) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

        if (isTransaction) {
            fragmentTransaction.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )

        }
        fragmentTransaction.replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commit()
    }
}

interface OnboardButtonListener {

    fun prevClickListener()
    fun nextClickListener()

}