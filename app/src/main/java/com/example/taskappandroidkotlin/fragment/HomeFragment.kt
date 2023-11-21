package com.example.taskappandroidkotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.taskappandroidkotlin.R
import com.example.taskappandroidkotlin.adapter.NoteAdapter
import com.example.taskappandroidkotlin.database.NoteDatabase
import com.example.taskappandroidkotlin.databinding.FragmentHomeBinding
import com.example.taskappandroidkotlin.entities.Notes

import kotlinx.coroutines.launch
import java.util.Locale


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    var arrNotes = ArrayList<Notes>()
    var noteAdapter: NoteAdapter = NoteAdapter()

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun initView() {
        binding.noteRcv.hasFixedSize()
        binding.noteRcv.layoutManager = StaggeredGridLayoutManager(2,VERTICAL)

        launch {
            context?.let {
                val notes = NoteDatabase.getDatabase(it).noteDao().getAllNote()
                noteAdapter?.setData(notes)
                binding.noteRcv.adapter = noteAdapter
            }
        }
        noteAdapter?.setOnClickListener(onClicked)

    }

    override fun initData() {
    }

    override fun initListener() {
        binding.fab.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(), false)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return  true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var tempArr = arrNotes

                for(arr in arrNotes){
                    if(arr.title?.toLowerCase(Locale.getDefault())?.contains(newText.toString()) == true){
                        tempArr.add(arr)
                    }
                }
                noteAdapter.setData(tempArr)
                noteAdapter.notifyDataSetChanged()
                return true
            }

        })
    }
    private val onClicked = object : NoteAdapter.onItemClickListener{
        override fun onClicked(noteId: Int) {
            var fragment:Fragment
            var bundle = Bundle()
            bundle.putInt("noteId",noteId)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle
            replaceFragment(fragment, false)
        }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
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