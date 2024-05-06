package com.cs411cmp003.bodywatchfrontend.ui.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cs411cmp003.bodywatchfrontend.data.response.FoodResponse
import com.cs411cmp003.bodywatchfrontend.databinding.RowFoodAdapterBinding
import com.cs411cmp003.bodywatchfrontend.ui.menuFood.FoodDetailActivity
import com.squareup.picasso.Picasso

class FoodAdapter() : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    var mList:ArrayList<FoodResponse> = ArrayList()
    inner class ViewHolder(var binding: RowFoodAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(foodItem: FoodResponse) {
            foodItem.let {
                binding.tvFoodName.text = it.prodName
                //picasso
                if (it.imageURL!=null && it.imageURL.isNotEmpty()) {
                    Picasso.get().load(it.imageURL).into(binding.ivFood)
                }


            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        RowFoodAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
        .apply {
            binding.apply {
                root.setOnClickListener {
                    //go to food detail page
                    val intent = Intent(binding.root.context, FoodDetailActivity::class.java)
                    intent.putExtra("food",mList[adapterPosition] as java.io.Serializable)
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

    fun setData(foodList: ArrayList<FoodResponse>){
        mList = foodList
        notifyDataSetChanged()
    }
    companion object{
        private val TAG = "FoodAdapter"
    }

}