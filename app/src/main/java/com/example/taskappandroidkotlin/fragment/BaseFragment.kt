package com.example.taskappandroidkotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<B : ViewBinding>:Fragment(), CoroutineScope {
    protected lateinit var binding: B
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, null)
        initView()
        initData()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()
    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

}