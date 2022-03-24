package com.example.foodselectionapp.ui.SaveFoodData

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

class SaveFoodViewmodel : ViewModel() {
    val SaveDataFlow = MutableStateFlow(State<Long>(StatusTypes.Nothing,""))
    val _saveDataFlow = SaveDataFlow.asStateFlow()

    fun saveFood(context: Context, foodItem: FoodItem, repo: SaveFoodRepo) {
        CoroutineScope(Dispatchers.IO).launch {
               CoroutineScope(Dispatchers.Main).launch {
                   SaveDataFlow.value= State(StatusTypes.Loading,"")
               }
           val response= repo.saveFood(context = context, foodItem = foodItem)
            CoroutineScope(Dispatchers.Main).launch {
               // SaveDataFlow.value= State(StatusTypes.Success,"",response)
                SaveDataFlow.value= State(StatusTypes.Success,"",response)
            }
        }
    }

    suspend fun hasFood(context: Context, foodItem: String, repo: SaveFoodRepo): Boolean {
        return repo.hasFood(context = context, id = foodItem)
    }
}