package com.example.foody.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foody.pojo.Category
import com.example.foody.pojo.CategoryItemList
import com.example.foody.pojo.CategoryList
import com.example.foody.pojo.CategoryMeal
import com.example.foody.pojo.Meal
import com.example.foody.pojo.MealList
import com.example.foody.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularMealLiveData = MutableLiveData<List<CategoryMeal>>()
    private var categoryItemsListLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val randomMeal = response.body()!!.meals[0]
                    Log.d("HomeFragment",randomMeal.strMeal)
                    randomMealLiveData.value = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment",t.toString())
            }
        })
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }


    fun getPopularItems(){
        RetrofitInstance.api.getMealCategory("Seafood").enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body()!=null){
                    popularMealLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment",t.toString())
            }
        })
    }

    fun observePopularItemMealLiveData():LiveData<List<CategoryMeal>>{
        return popularMealLiveData
    }

    fun getCategoryItemsList(){
        RetrofitInstance.api.getMealCategoryList().enqueue(object : Callback<CategoryItemList>{
            override fun onResponse(
                call: Call<CategoryItemList>,
                response: Response<CategoryItemList>
            ) {
                if (response.body()!=null){
                    categoryItemsListLiveData.value = response.body()!!.categories
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CategoryItemList>, t: Throwable) {
                Log.d("HomeFragment",t.toString())
            }
        })
    }

    fun observeCategoryItemsLiveData() : LiveData<List<Category>>{
        return categoryItemsListLiveData
    }

}