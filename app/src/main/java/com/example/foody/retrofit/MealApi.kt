package com.example.foody.retrofit

import com.example.foody.pojo.CategoryItemList
import com.example.foody.pojo.CategoryList
import com.example.foody.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi{

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id:String):Call<MealList>

    @GET("filter.php?")
    fun getMealCategory(@Query("c")categoryName:String = "Seafood"):Call<CategoryList>

    @GET("categories.php")
    fun getMealCategoryList():Call<CategoryItemList>
}