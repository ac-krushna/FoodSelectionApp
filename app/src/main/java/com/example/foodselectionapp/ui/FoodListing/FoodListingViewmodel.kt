package com.example.foodselectionapp.ui.FoodListing

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.foodselectionapp.model.FoodItem
import com.example.foodselectionapp.model.State
import com.example.foodselectionapp.model.StatusTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodListingViewmodel : ViewModel() {
    val foodListing = MutableStateFlow(State<ArrayList<FoodItem>>(StatusTypes.Loading,""))
    val _foodListing = foodListing.asStateFlow()
    fun getFoodListing(context: Context, foodListingRepo: FoodListingRepo, wantAscending: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.Main).launch {
                foodListing.value = State(StatusTypes.Loading, "")
            }
            foodListingRepo.getFoodListing(context, wantAscending = wantAscending).let {
                CoroutineScope(Dispatchers.Main).launch {
                    if(it.size>0)
                        foodListing.value =State(StatusTypes.Success,"",it)
                    else
                        foodListing.value =State(StatusTypes.Error,"Data Not Found")
                }
            }
        }
    }
}