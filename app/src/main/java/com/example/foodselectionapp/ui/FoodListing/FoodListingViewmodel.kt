package com.example.foodselectionapp.ui.FoodListing

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodselectionapp.model.FoodItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodListingViewmodel:ViewModel() {
    val foodListing= MutableStateFlow(ArrayList<FoodItem>())
    val _foodListing= foodListing.asStateFlow()
fun getFoodListing(context: Context,foodListingRepo: FoodListingRepo){
    CoroutineScope(Dispatchers.IO).launch {
        foodListingRepo.getFoodListing(context).let {
            CoroutineScope(Dispatchers.Main).launch{
                foodListing.value=it
                foodListing.emit(it)
            }
        }
    }
}
}