package com.example.taskappandroidkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskappandroidkotlin.R
import com.example.taskappandroidkotlin.entities.OnBoardModel

class OnBoardViewModel: ViewModel() {

    val boardingLiveData = MutableLiveData<List<OnBoardModel>>()

    init {
        getOnBoardData()
    }

    private fun getOnBoardData() {
        boardingLiveData.postValue(

            listOf(
                OnBoardModel(
                    R.drawable.note_one,
                    "Tien loi vcl",
                "Qua la dep trai"
                ),
                OnBoardModel(
                    R.drawable.note_two,
                    "Quen gi thi ghi lai, ko phai flo",
                    "Qua la tuyet voi"
                ),
                OnBoardModel(
                    R.drawable.note_threee,
                    "Rieng cai app nay, no la ghe",
                    "Vi don gian no dell phai ban"
                ),
            )

        )
    }
}