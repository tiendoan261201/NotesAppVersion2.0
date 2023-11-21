package com.example.taskappandroidkotlin.fragment

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PathMeasure
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.taskappandroidkotlin.R
import com.example.taskappandroidkotlin.database.NoteDatabase
import com.example.taskappandroidkotlin.databinding.FragmentCreateNoteBinding
import com.example.taskappandroidkotlin.entities.Notes
import com.example.taskappandroidkotlin.util.NotesBottomSheetFragment
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date


class CreateNoteFragment : BaseFragment<FragmentCreateNoteBinding>() {

    var selectedColor = "#171C26"
    var currentDate:String? = null
//    private var READ_STORAGE_PERMISSION = 123
//    private var REQUEST_CODE_IMAGE = 456
    private var selectedImagePath = ""
    private var webLink = ""
    private var noteId = -1


    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = requireArguments().getInt("noteId")

    }

    override fun initView() {
        noteId = requireArguments().getInt("noteId",-1)

        if (noteId != -1){
            launch {
                context?.let {
                    var notes = NoteDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                    binding.etNoteTitle.setText(notes.title)
                    binding.etNoteSubTitle.setText(notes.subTitle)
                    binding.etNoteDesc.setText(notes.description)
                    if (notes.imgPath != ""){
                        selectedImagePath = notes.imgPath.toString()
                        binding.imgNote.setImageURI(Uri.parse(notes.imgPath))
                        binding.layoutImage.visibility = View.VISIBLE
                        binding.imgNote.visibility = View.VISIBLE
                        binding.imgDelete.visibility = View.VISIBLE
                    }else{
                        binding.layoutImage.visibility = View.GONE
                        binding.imgNote.visibility = View.GONE
                        binding.imgDelete.visibility = View.GONE
                    }

                    if (notes.webLink != ""){
                        webLink = notes.webLink.toString()
                        binding.tvWebLink.text = notes.webLink
                        binding.layoutWebUrl.visibility = View.VISIBLE
                        binding.etWebLink.setText(notes.webLink)
                        binding.imgUrlDelete.visibility = View.VISIBLE
                    }else{
                        binding.imgUrlDelete.visibility = View.GONE
                        binding.layoutWebUrl.visibility = View.GONE
                    }

                }
            }
        }

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        binding.tvDateTime.text = currentDate
        binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
    }

    override fun initData() {

    }

    override fun initListener() {
        binding.imgDone.setOnClickListener {
            saveNote()
        }
        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()

        }
        binding.imgMore.setOnClickListener {
            var noteBottomSheetfragment = NotesBottomSheetFragment.newInstance(noteId)
            noteBottomSheetfragment.show(requireActivity().supportFragmentManager,"Note Bottom Sheet Fragment")

        }
        binding.btnOk.setOnClickListener {
            if (binding.etWebLink.text.toString().trim().isNotEmpty()){
                checkWebUrl()
            }else{
                Toast.makeText(requireContext(),"URL is required",Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCancel.setOnClickListener {
            if(noteId != - 1){
                binding.tvWebLink.visibility = View.GONE
                binding.layoutWebUrl.visibility = View.GONE
            }else{
            binding.layoutWebUrl.visibility = View.GONE
            }
        }
        binding.tvWebLink.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW,Uri.parse(binding.etWebLink.text.toString()))
            startActivity(intent)
        }

        binding.imgUrlDelete.setOnClickListener {
            webLink = ""
            binding.tvWebLink.visibility = View.GONE
            binding.imgUrlDelete.visibility = View.GONE
            binding.layoutWebUrl.visibility = View.GONE
        }

        binding.imgDelete.setOnClickListener {
            selectedImagePath = ""
            binding.layoutImage.visibility = View.GONE

        }
        binding.imgDone.setOnClickListener {
            if(noteId != -1){
                updateNote()
            }else{
                saveNote()
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateNoteBinding {
        return   FragmentCreateNoteBinding.inflate(inflater, container, false)

    }
    private fun updateNote(){
        launch {

            context?.let {
                var notes = NoteDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)

                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.description = binding.etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                notes.imgPath = selectedImagePath
                notes.webLink = webLink

                NoteDatabase.getDatabase(it).noteDao().updateNote(notes)
                binding.etNoteTitle.setText("")
                binding.etNoteSubTitle.setText("")
                binding.etNoteDesc.setText("")
                binding.layoutImage.visibility = View.GONE
                binding. imgNote.visibility = View.GONE
                binding.tvWebLink.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun saveNote() {
        if(binding.etNoteTitle.text.isNullOrEmpty()){
            Toast.makeText(context,"Note Title is required", Toast.LENGTH_SHORT).show()
        }
        else if(binding.etNoteSubTitle.text.isNullOrEmpty()){
            Toast.makeText(context,"Note Sub Title is required", Toast.LENGTH_SHORT).show()
        }
        else if(binding.etNoteDesc.text.isNullOrEmpty()){
            Toast.makeText(context,"Context is required", Toast.LENGTH_SHORT).show()
        }
        else{
            launch {
                var notes = Notes()
                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.description = binding.etNoteDesc.text.toString()
                notes.color = selectedColor
                notes.dateTime = currentDate
                notes.imgPath = selectedImagePath
                notes.webLink = webLink
                context?.let {
                    NoteDatabase.getDatabase(it).noteDao().insertNotes(notes)
                    binding.etNoteTitle.setText("")
                    binding.etNoteSubTitle.setText("")
                    binding.etNoteDesc.setText("")
                    binding.layoutImage.visibility =View.GONE
                    binding.imgNote.visibility = View.GONE
                    binding.tvWebLink.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }

            }
        }

    }


    private fun checkWebUrl(){
        if(Patterns.WEB_URL.matcher(binding.etWebLink.text.toString()).matches()){
           binding.layoutWebUrl.visibility = View.GONE
            binding.etWebLink.isEnabled = false
            webLink =  binding.etWebLink.text.toString()
            binding.tvWebLink.visibility = View.VISIBLE
            binding.tvWebLink.text = webLink
        }else{
            Toast.makeText(requireContext(),"URL is not available",Toast.LENGTH_SHORT).show()
        }

    }

    private val broadcastReceiver: BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            var actionColor = intent!!.getStringExtra("action")

            when(actionColor!!){
                "blue" ->{
                     selectedColor = intent.getStringExtra("selectedColor").toString()
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
                "yellow" ->{
                     selectedColor = intent.getStringExtra("selectedColor").toString()
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Purple" ->{
                     selectedColor = intent.getStringExtra("selectedColor").toString()
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "orange" ->{
                     selectedColor = intent.getStringExtra("selectedColor").toString()
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "green" ->{
                     selectedColor = intent.getStringExtra("selectedColor").toString()
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "black" ->{
                     selectedColor = intent.getStringExtra("selectedColor").toString()
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Image"->{
                   pickImageFromGallery()
                    binding.layoutWebUrl.visibility = View.GONE
                }
                "WebUrl" ->{
                   binding.layoutImage.visibility = View.GONE
                    binding.layoutWebUrl.visibility = View.VISIBLE
                }
                "deleteNote" ->{
                    deleteNote()

                }

                else ->{
                    binding.layoutImage.visibility =View.GONE
                    binding.imgNote.visibility = View.GONE
                    binding.layoutWebUrl.visibility = View.GONE
                     selectedColor = intent.getStringExtra("selectedColor").toString()
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
            }
        }

    }

    private fun deleteNote() {
        launch {
            context?.let {
                NoteDatabase.getDatabase(it).noteDao().deleteSpecificNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroy() {

        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }


    private fun pickImageFromGallery(){
        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .start()
        Log.d("AAAA","${binding.imgNote}")
    }

    private fun getPathFromUrl(contentUri: Uri):String?{
        var filePath:String? = null
        var cursor = requireActivity().contentResolver.query(contentUri,null,null,null,null)
        if(cursor == null){
            filePath = contentUri.path
        }else{
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ImagePicker.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            binding.imgNote.setImageURI(data?.data)
            binding.imgNote.visibility = View.VISIBLE
            Log.d("AAAA", "pickerImage = ${binding.imgNote}")
            selectedImagePath  = data?.data?.let { getPathFromUrl(it) }!!
        }
    }

}