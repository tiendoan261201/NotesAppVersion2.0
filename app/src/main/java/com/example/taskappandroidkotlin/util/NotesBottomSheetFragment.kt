package com.example.taskappandroidkotlin.util

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.taskappandroidkotlin.R
import com.example.taskappandroidkotlin.databinding.FragmentNotesBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotesBottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotesBottomSheetBinding
    var selectedColor = "#171C26"

    companion object{
        var noteId = -1
        fun newInstance(id:Int) :NotesBottomSheetFragment{
            val args = Bundle()
            val fragment = NotesBottomSheetFragment()
            fragment.arguments = args
            noteId = id
            return fragment
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_sheet,null)
        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = param.behavior

        if(behavior is BottomSheetBehavior<*>){
            behavior.setBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when (newState){
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = "COLLAPSED"
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }

                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

            })

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(noteId != -1){
            binding.layoutDeleteNote.visibility = View.VISIBLE
        }else{
            binding.layoutDeleteNote.visibility = View.GONE
        }
        setListen()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun setListen(){


        binding.fNote1.setOnClickListener{
            binding.imgNote1.setImageResource(R.drawable.ic_tick)
            binding.imgNote2.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#4e33ff"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","blue")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.fNote2.setOnClickListener{
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(R.drawable.ic_tick)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#ffd633"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","yellow")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.fNote4.setOnClickListener{
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote4.setImageResource(R.drawable.ic_tick)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#ae3b76"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","purple")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.fNote5.setOnClickListener{
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(R.drawable.ic_tick)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#0aebaf"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","green")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.fNote6.setOnClickListener{
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(R.drawable.ic_tick)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#ff7746"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","orange")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.fNote7.setOnClickListener{
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(R.drawable.ic_tick)
            selectedColor = "#202734"
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","black")
            intent.putExtra("selectedColor",selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.layoutImage.setOnClickListener{
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
        binding.layoutWebUrl.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","WebUrl")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
        binding.layoutDeleteNote.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action","deleteNote")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
    }
}