package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foody.databinding.PopularItemsBinding
import com.example.foody.pojo.CategoryMeal

class MostPopularMealAdapter(): RecyclerView.Adapter<MostPopularMealAdapter.PopularMealViewHolder>() {
    private var mealLst = ArrayList<CategoryMeal>()
    lateinit var onItemClick : ((CategoryMeal) -> Unit)

    fun setMeals(mealList:ArrayList<CategoryMeal>){
        this.mealLst = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent
            .context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealLst[position].strMealThumb)
            .into(holder.binding.ivPopularItems)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealLst[position])
        }
    }

    override fun getItemCount(): Int {
        return mealLst.size
    }

    class PopularMealViewHolder (val binding: PopularItemsBinding): RecyclerView.ViewHolder
        (binding.root)

}