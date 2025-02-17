package com.example.foody.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foody.activities.MealActivity
import com.example.foody.adapters.MostPopularMealAdapter
import com.example.foody.databinding.FragmentHomeBinding
import com.example.foody.mvvm.HomeViewModel
import com.example.foody.pojo.CategoryMeal
import com.example.foody.pojo.Meal

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularMealAdapter


    companion object{
        const val MEAL_ID = "com.example.foody.fragments.mealId"
        const val MEAL_NAME = "com.example.foody.fragments.mealName"
        const val MEAL_THUMB = "com.example.foody.fragments.mealThumb"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemAdapter = MostPopularMealAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerview()

        homeMvvm.getRandomMeal()
        observerRandomMealLiveData()
        openMealActivity()

        homeMvvm.getPopularItems()
        observePopularItemLiveData()

        onPopularItemClicked()
    }

    private fun onPopularItemClicked() {
        popularItemAdapter.onItemClick = { meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_NAME,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerview() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemAdapter
        }
    }

    private fun observePopularItemLiveData() {
        homeMvvm.observePopularItemMealLiveData().observe(viewLifecycleOwner,object : Observer<List<CategoryMeal>>{
            override fun onChanged(value: List<CategoryMeal>) {
                popularItemAdapter.setMeals(mealList = value as ArrayList<CategoryMeal>)
            }
        })
    }

    private fun observerRandomMealLiveData() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,object : Observer<Meal>{
            override fun onChanged(randomMeal: Meal) {
                Glide.with(this@HomeFragment)
                    .load(randomMeal.strMealThumb)
                    .into(binding.imgRandomMeal)

                this@HomeFragment.randomMeal = randomMeal
            }
        })
    }

    private fun openMealActivity() {
        binding.imgRandomMeal.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

}