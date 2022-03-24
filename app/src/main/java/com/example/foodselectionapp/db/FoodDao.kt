package com.example.foodselectionapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodselectionapp.model.FoodItem

@Dao
interface FoodDao {
    @Query("Select * from FoodItem order by " +
            "case when :wantAscending =1  then foodName  END ASC" +
            ", case when :wantAscending =0  then foodName END DESC")
    suspend fun getFoodList(wantAscending:Boolean=true): List<FoodItem>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFood(foodItem: FoodItem): Long

    @Query("Select * from FoodItem where foodId = :foodId")
    suspend fun checkIfDataAdded(foodId: String): FoodItem?

    @Delete
    suspend fun deleteFood(foodItem: FoodItem)

}