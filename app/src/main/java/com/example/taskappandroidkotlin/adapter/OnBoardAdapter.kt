package com.example.taskappandroidkotlin.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskappandroidkotlin.databinding.ItemOnboardingBinding
import com.example.taskappandroidkotlin.entities.OnBoardModel

class OnBoardAdapter(private var onboardList: List<OnBoardModel>):
    RecyclerView.Adapter<OnBoardAdapter.OnBoardViewHolder>()
{
    inner class OnBoardViewHolder(private  val bnd: ItemOnboardingBinding): RecyclerView.ViewHolder(bnd.root){
        fun bind(onBoardModel: OnBoardModel){
            bnd.dataHolder = onBoardModel
            bnd.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardViewHolder {
        return OnBoardViewHolder(ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return onboardList.size
    }

    override fun onBindViewHolder(holder: OnBoardViewHolder, position: Int) {
        holder.bind(onboardList[position])
    }
}