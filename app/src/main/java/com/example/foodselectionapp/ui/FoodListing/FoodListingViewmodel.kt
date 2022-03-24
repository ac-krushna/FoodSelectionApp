package com.example.foodselectionapp.ui.FoodListing

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodselectionapp.model.FoodItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodListingViewmodel : ViewModel() {
    val foodListing = MutableStateFlow(ArrayList<FoodItem>())
    val _foodListing = foodListing.asStateFlow()
    fun getFoodListing(context: Context, foodListingRepo: FoodListingRepo, wantAscending: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            foodListingRepo.getFoodListing(context, wantAscending = wantAscending).let {
                CoroutineScope(Dispatchers.Main).launch {
                    foodListing.value = it
                    foodListing.emit(it)
                }
            }
        }
    }
}