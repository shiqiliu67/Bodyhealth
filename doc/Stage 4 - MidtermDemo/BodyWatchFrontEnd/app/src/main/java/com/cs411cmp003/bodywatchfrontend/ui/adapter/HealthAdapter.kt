package com.cs411cmp003.bodywatchfrontend.ui.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cs411cmp003.bodywatchfrontend.data.response.GoalResponse
import com.cs411cmp003.bodywatchfrontend.data.response.HealthResponse
import com.cs411cmp003.bodywatchfrontend.databinding.RowHealthAdapterBinding
import com.cs411cmp003.bodywatchfrontend.ui.menuHealth.HealthDetailActivity

class HealthAdapter() : RecyclerView.Adapter<HealthAdapter.ViewHolder>() {
    var mList: ArrayList<HealthResponse> = ArrayList()
    inner class ViewHolder(var binding: RowHealthAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(discoverItem: HealthResponse) {
            discoverItem.let {
                binding.tvHealthDate.text = it.date
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        RowHealthAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
        .apply {
            binding.apply {
                root.setOnClickListener {
                    //go to food detail page
                    val intent: Intent =
                        Intent(binding.root.context, HealthDetailActivity::class.java)
                    intent.putExtra("health",mList[adapterPosition] as java.io.Serializable)
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

    fun setData(healthList: ArrayList<HealthResponse>){
        mList = healthList
        notifyDataSetChanged()
    }
    companion object {
        private val TAG = "HealthAdapter"
    }
}
