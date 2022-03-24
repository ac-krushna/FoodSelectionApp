package com.example.foodselectionapp.ui.SaveFoodData

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodselectionapp.model.FoodItem

class SaveFoodViewmodel : ViewModel() {
    suspend fun saveFood(context: Context, foodItem: FoodItem, repo: SaveFoodRepo): Long {
        return repo.saveFood(context = context, foodItem = foodItem)
    }

    suspend fun hasFood(context: Context, foodItem: String, repo: SaveFoodRepo): Boolean {
        return repo.hasFood(context = context, id = foodItem)
    }
}