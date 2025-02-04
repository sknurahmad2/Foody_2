package com.example.foody.retrofit

import com.example.foody.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi{

    @GET("random.php")
    fun getRandomMeal():Call<MealList>
}