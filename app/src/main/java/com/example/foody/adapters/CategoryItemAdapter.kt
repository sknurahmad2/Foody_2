package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foody.adapters.MostPopularMealAdapter.PopularMealViewHolder
import com.example.foody.databinding.CategoryItemsBinding
import com.example.foody.databinding.PopularItemsBinding
import com.example.foody.pojo.Category
import com.example.foody.pojo.CategoryMeal

class CategoryItemAdapter(): RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>() {
    private var categoryList = ArrayList<Category>()

    fun setCategoryItems(list:ArrayList<Category>){
        categoryList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            CategoryItemsBinding.inflate(
                LayoutInflater.from(parent
            .context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.ivCategpryItems)

        holder.binding.tvCategoryItem.text = categoryList[position].strCategory
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryItemViewHolder (val binding: CategoryItemsBinding) : RecyclerView.ViewHolder(binding.root)
}