package com.example.foodselectionapp.ui

import android.os.Bundle
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.foodselectionapp.model.FoodItem
const val FOOD_ID="foodId"
const val FOOD_NAME="foodName"
const val FOOD_PRICE="foodPrice"
const val FOOD_BRAND="foodBrand"
sealed class Screens(var route:String){
    public object FoodListingScreen:Screens(route = "foodListing"){
        fun navigate(navController: NavHostController){
            navController.navigate(this.route)
        }
        fun popBack(navController: NavHostController){
            navController.popBackStack(this.route,false)
        }
    }
    public object FoodDetailScreen:Screens(route = "foodDetails/{${FOOD_ID}}/{${FOOD_NAME}}/{${FOOD_PRICE}}/{${FOOD_BRAND}}"){
        fun navigate(navController: NavHostController,foodItem: FoodItem){
            this.route="foodDetails/${foodItem.foodId}/${foodItem.foodName}/${foodItem.foodPrice}/${foodItem.foodBrand}"
            navController.navigate(this.route)
        }
        fun popBack(navController: NavHostController){
            navController.popBackStack(this.route,false)
        }

        fun wrapArguments(): List<NamedNavArgument> {
           return listOf(
                navArgument(FOOD_ID) {
                    type = NavType.StringType
                    defaultValue = "0"
                },
                navArgument(FOOD_NAME) {
                    type = NavType.StringType
                    defaultValue = "Test"
                },
                navArgument(FOOD_PRICE) {
                    type = NavType.StringType
                    defaultValue = "0"
                },
                navArgument(FOOD_BRAND) {
                    type = NavType.StringType
                    defaultValue = "Unknown"
                }
            )
        }

        fun giveFoodId(arguments: Bundle?):String {
            return arguments?.getString(FOOD_ID,"0") ?:"0"
        }
        fun giveFoodName(arguments: Bundle?):String {
            return arguments?.getString(FOOD_NAME,"Test") ?:"Test"
        }
        fun giveFoodBrand(arguments: Bundle?):String {
            return arguments?.getString(FOOD_BRAND,"Unknown") ?:"Unknown"
        }
        fun giveFoodPrice(arguments: Bundle?):String {
            return arguments?.getString(FOOD_PRICE,"0") ?:"0"
        }
    }
    public object CreateFoodScreen:Screens(route = "saveFoodDetails"){
        fun navigate(navController: NavHostController){
            navController.navigate(this.route)
        }
        fun popBack(navController: NavHostController){
            navController.popBackStack(this.route,false)
        }
    }
}

