package com.example.foodselectionapp.ui.SaveFoodData

import android.content.Context
import com.example.foodselectionapp.db.AppDatabase
import com.example.foodselectionapp.model.FoodItem

class SaveFoodRepo {
    suspend fun saveFood(context: Context, foodItem: FoodItem):Long{
        val db = AppDatabase.getDatabase(context)
        val dao = db.dao()
        return dao.addFood(foodItem = foodItem)
    }

    suspend fun hasFood(context: Context, id: String):Boolean{
        val db = AppDatabase.getDatabase(context)
        val dao = db.dao()
        return dao.checkIfDataAdded(foodId = id)!=null
    }
}