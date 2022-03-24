package com.example.foodselectionapp.ui.FoodListing

import android.content.Context
import android.util.Log
import com.example.foodselectionapp.db.AppDatabase
import com.example.foodselectionapp.model.FoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodListingRepo {
    suspend fun getFoodListing(context: Context,wantAscending: Boolean): ArrayList<FoodItem> =
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(context)
            val dao = db.dao()
            if (dao.getFoodList(wantAscending) != null)
                ArrayList(dao.getFoodList(wantAscending)!!)
            else {
                Log.e("getFoodListing", "getFoodListing: No Data Found for Food")
                ArrayList()
            }
        }
}