package com.javohirbekcoder.banner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.javohirbekcoder.banner.databinding.RecyclerItemHorizontalBinding
import com.javohirbekcoder.banner.databinding.RecyclerItemVerticalBinding
import com.javohirbekcoder.banner.model.MyItem

/*
Created by Javohirbek on 05.03.2023 at 22:15
*/
class MyHorizontalAdapter(private val list: ArrayList<MyItem>) :
    RecyclerView.Adapter<MyHorizontalAdapter.MyViewHoler>() {
    inner class MyViewHoler(val binding: RecyclerItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBing(myItem: MyItem) {
            Glide.with(binding.root.context).load(myItem.imgUrl).into(binding.imageView)
            binding.titleTv.text = myItem.titleText
            binding.descriptionTv.text = myItem.descText
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoler {
        return MyViewHoler(
            RecyclerItemHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHoler, position: Int) {
        holder.onBing(list[position])
    }
}