package com.example.foody.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foody.databinding.ActivityMealBinding
import com.example.foody.fragments.HomeFragment
import com.example.foody.mvvm.HomeViewModel
import com.example.foody.mvvm.MealViewModel
import com.example.foody.pojo.Meal

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformation()

        setReceivedInformation()

        loadingCase()
        mealMvvm.getMealDetails(mealId)
        observeMealDetailsLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYouTube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this,object: Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value

                binding.tvCategory.text = "Category : "+ meal.strCategory
                binding.tvArea.text = "Area : "+meal.strArea
                binding.tvInstructionDetails.text = meal.strInstructions

                youtubeLink = meal.strYoutube
            }
        })
    }

    private fun getMealInformation() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID).toString()
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME).toString()
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB).toString()

    }

    private fun setReceivedInformation() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetails)
        
        binding.collapsingToolbar.title = mealName
    }

    private fun loadingCase(){
        binding.progressbar.visibility = View.VISIBLE
        binding.floatingBtnFav.visibility = View.INVISIBLE
        binding.imgYouTube.visibility = View.INVISIBLE
        binding.tvCategory.text = "Category : Loading..."
        binding.tvArea.text = "Area : Loading..."
        binding.tvInstructionDetails.text = "Loading..."

    }

    private fun onResponseCase(){
        binding.progressbar.visibility = View.INVISIBLE
        binding.floatingBtnFav.visibility = View.VISIBLE
        binding.imgYouTube.visibility = View.VISIBLE
    }
}