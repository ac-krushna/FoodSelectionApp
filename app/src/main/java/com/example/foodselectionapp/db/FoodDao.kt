package com.example.foodselectionapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodselectionapp.model.FoodItem

@Dao
interface FoodDao {
    @Query("Select * from FoodItem")
    suspend fun getFoodList():List<FoodItem>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFood(foodItem: FoodItem):Long

    @Query("Select * from FoodItem where foodId = :foodId")
    suspend fun checkIfDataAdded(foodId:String):FoodItem?

}