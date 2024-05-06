package com.cs411cmp003.bodywatchfrontend.ui.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cs411cmp003.bodywatchfrontend.data.response.GoalResponse
import com.cs411cmp003.bodywatchfrontend.databinding.RowGoalsAdapterBinding
import com.cs411cmp003.bodywatchfrontend.ui.menuGoal.GoalDetailActivity

class GoalAdapter(): RecyclerView.Adapter<GoalAdapter.ViewHolder>() {
    var mList:ArrayList<GoalResponse> = ArrayList()
    inner class ViewHolder(var binding: RowGoalsAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(discoverItem: GoalResponse) {
            discoverItem.let {
                binding.tvGoalName.text = it.timeline

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        RowGoalsAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
        .apply {
            binding.apply {
                root.setOnClickListener {
                    //go to food detail page
                    val intent: Intent = Intent(binding.root.context, GoalDetailActivity::class.java)
                    intent.putExtra("goal",mList[adapterPosition] as java.io.Serializable)
                    binding.root.context.startActivity(intent)
                }

            }

        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(goalList: ArrayList<GoalResponse>){
        mList = goalList
        notifyDataSetChanged()
    }
    companion object {
        private val TAG = "FoodAdapter"
    }

}