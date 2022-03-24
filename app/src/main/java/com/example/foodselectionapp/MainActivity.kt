package com.example.foodselectionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodselectionapp.model.FoodItem
import com.example.foodselectionapp.ui.FoodDetails.FoodDetails
import com.example.foodselectionapp.ui.FoodListing.ShowFoodListing
import com.example.foodselectionapp.ui.SaveFoodData.SaveFoodFlow
import com.example.foodselectionapp.ui.Screens
import com.example.foodselectionapp.ui.theme.FoodSelectionAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartingAppView()
        }
    }
}


@OptIn(ExperimentalUnitApi::class)
@Composable
fun StartingAppView() {
    FoodSelectionAppTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screens.FoodListingScreen.route) {
            composable(Screens.FoodListingScreen.route) {
                ShowFoodListing(
                    LocalContext.current,
                    navController
                )
            }
            composable(
                Screens.FoodDetailScreen.route,
                arguments = Screens.FoodDetailScreen.wrapArguments()
            ) {
                FoodDetails(
                    LocalContext.current, navController,
                    FoodItem(
                        foodId = Screens.FoodDetailScreen.giveFoodId(it.arguments),
                        foodName = Screens.FoodDetailScreen.giveFoodName(it.arguments),
                        foodBrand = Screens.FoodDetailScreen.giveFoodBrand(it.arguments),
                        foodPrice = Screens.FoodDetailScreen.giveFoodPrice(it.arguments),
                    )
                )
            }
            composable(Screens.CreateFoodScreen.route) {
                SaveFoodFlow(
                    LocalContext.current,
                    navController
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StartingAppView()
}