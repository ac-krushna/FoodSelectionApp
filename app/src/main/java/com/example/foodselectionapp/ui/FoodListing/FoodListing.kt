package com.example.foodselectionapp.ui.FoodListing

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.foodselectionapp.model.FoodItem
import com.example.foodselectionapp.ui.theme.FoodSelectionAppTheme
import com.example.foodselectionapp.ui.theme.getHeaderTextcolor

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ShowFoodListing(navController: NavHostController?=null) {
    val listState = rememberLazyListState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Available Foods",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = TextUnit(18f, TextUnitType.Sp),
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                        )
                    },
                    actions = {

                    }
                )
            },
        ) {
            LazyColumn(
                state = listState,
                content = {
                    for (i in 0..50) {
                        item {
                            FoodItemCell{
                                navController?.navigate("foodDetails")
                        }
                        }
                    }
                },
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun FoodItemCell(foodItem: FoodItem? = null,onItemClick:()->Unit) {
    Row(Modifier.padding(bottom = 5.dp, start = 10.dp, end = 10.dp).clickable {
        onItemClick()
    }) {
        AsyncImage(
            model = "https://via.placeholder.com/300/09f.png/fff",
            contentDescription = "description of the image",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column(Modifier.padding(start = 5.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = foodItem?.foodName ?: "Test Name",
                color = getHeaderTextcolor(),
                fontWeight = FontWeight.SemiBold,
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = foodItem?.foodBrand ?: "Brand ",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(170.dp)
                        .padding(end = 10.dp)
                )
                Text(
                    foodItem?.foodPrice?.toString() ?: "Rs. 100000000000000",
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(170.dp)
                )

            }

        }
    }
}

@Preview(name = "light", showBackground = true, showSystemUi = true)
@Preview(name = "dark", uiMode = UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true)
@Composable
fun DefaultPreview() {
    FoodSelectionAppTheme {
        ShowFoodListing()
    }
}