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
import com.example.foodselectionapp.ui.FoodDetails.FoodDetails
import com.example.foodselectionapp.ui.FoodListing.ShowFoodListing
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
        NavHost(navController = navController, startDestination = "foodListing") {
            composable("foodListing") { ShowFoodListing(navController)}
            composable("foodDetails") { FoodDetails(LocalContext.current,navController) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StartingAppView()
}