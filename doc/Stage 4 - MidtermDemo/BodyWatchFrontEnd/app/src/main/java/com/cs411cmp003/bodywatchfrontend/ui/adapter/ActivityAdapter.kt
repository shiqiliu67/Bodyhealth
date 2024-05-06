package com.cs411cmp003.bodywatchfrontend.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cs411cmp003.bodywatchfrontend.data.response.ActivityResponse
import com.cs411cmp003.bodywatchfrontend.data.response.GoalResponse
import com.cs411cmp003.bodywatchfrontend.databinding.RowActivityAdapterBinding
import com.cs411cmp003.bodywatchfrontend.ui.menuActivity.ActiviesActivity


class ActivityAdapter: RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {
    var mList:ArrayList<ActivityResponse> = ArrayList()
    inner class ViewHolder(var binding: RowActivityAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(discoverItem: ActivityResponse) {
            discoverItem.let {
                binding.tvActivityName.text = "${it.startTime} - ${it.endTime}"

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        RowActivityAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
        .apply {
            binding.apply {
                root.setOnClickListener {
                    //go to food detail page
                    val intent: Intent = Intent(binding.root.context, ActiviesActivity::class.java)
                     intent.putExtra("activity",mList[adapterPosition] as java.io.Serializable)
                    binding.root.context.startActivity(intent)
                }

            }

        }

    fun setData(goalList: ArrayList<ActivityResponse>){
        mList = goalList
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ActivityAdapter.ViewHolder, position: Int) {
        return holder.bind(mList[position])
    }


    override fun getItemCount(): Int {
        return mList.size
    }



    companion object{
        private val TAG = "FoodAdapter"
    }

}