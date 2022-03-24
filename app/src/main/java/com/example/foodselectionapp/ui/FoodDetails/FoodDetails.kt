package com.example.foodselectionapp.ui.FoodDetails

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.foodselectionapp.R
import com.example.foodselectionapp.model.FoodItem
import com.example.foodselectionapp.ui.Screens
import com.example.foodselectionapp.ui.theme.FoodSelectionAppTheme
import com.example.foodselectionapp.ui.theme.getHeaderTextcolor
import com.example.foodselectionapp.ui.theme.getSubHeaderTextcolor

@OptIn(ExperimentalUnitApi::class)
@Composable
fun FoodDetails(
    context: Context,
    navController: NavHostController? = null,
    foodItemDetails: FoodItem?
) {
    val listState = rememberLazyListState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            Modifier
                .fillMaxSize(),

            ) {
            Box {
                //Box() {
                LazyColumn(
                    horizontalAlignment = CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    content = {
                        item {
                            AsyncImage(
                                model = "https://media.istockphoto.com/photos/grilled-chicken-meat-and-fresh-vegetable-salad-of-tomato-avocado-and-picture-id1295633127",
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(20.dp)
                                    .width(100.dp)
                                    .height(100.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = foodItemDetails?.foodName ?: "",
                                fontSize = TextUnit(25f, TextUnitType.Sp),
                                fontWeight = FontWeight.SemiBold,
                                color = getHeaderTextcolor()
                            )
                        }
                        item {
                            Text(
                                text = "Brand: ${foodItemDetails?.foodBrand} Price : ${foodItemDetails?.foodPrice}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = TextUnit(14f, TextUnitType.Sp),
                                color = getSubHeaderTextcolor(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, start = 8.dp, end = 8.dp),

                                )
                        }
                    }
                )

                Box(
                    Modifier
                        .clip(CircleShape)
                        .clickable {
                            Screens.FoodListingScreen.popBack(navController = navController!!)
                        }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = "",
                        Modifier
                            .padding(8.dp)
                            .width(50.dp)
                            .height(50.dp)
                            .padding(12.dp)
                            .clip(CircleShape),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                    )
                }
            }

        }
    }
}


@Preview(name = "light", showBackground = true, showSystemUi = true)
@Preview(
    name = "dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true,
    showBackground = true
)
@Composable
fun DefaultPreview() {
    FoodSelectionAppTheme {
        FoodDetails(LocalContext.current, foodItemDetails = FoodItem("","","",""))
    }
}