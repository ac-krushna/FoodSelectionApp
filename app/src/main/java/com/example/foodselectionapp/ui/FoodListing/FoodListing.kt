package com.example.foodselectionapp.ui.FoodListing

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.foodselectionapp.R
import com.example.foodselectionapp.model.FoodItem
import com.example.foodselectionapp.model.StatusTypes
import com.example.foodselectionapp.ui.Screens
import com.example.foodselectionapp.ui.theme.FoodSelectionAppTheme
import com.example.foodselectionapp.ui.theme.getHeaderTextcolor

@OptIn(ExperimentalUnitApi::class, ExperimentalMaterialApi::class)
@Composable
fun ShowFoodListing(context: Context, navController: NavHostController? = null) {
    val swipeableState = rememberSwipeableState(0)
    val squareSize = 100.dp
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1)

    val listState = rememberLazyListState()
    val expanded = remember { mutableStateOf(false) }
    val selectedFilter = remember { mutableStateOf("A-Z") }
    val foodListingViewmodel = viewModel<FoodListingViewmodel>()
    val foodResponse = foodListingViewmodel._foodListing.collectAsState().value
    foodListingViewmodel.getFoodListing(context, FoodListingRepo(), selectedFilter.value == "A-Z")
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
                        Image(painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                            modifier = Modifier.clickable {
                                expanded.value = !expanded.value
                            })
                        FilterMenu(expanded, selectedFilter)
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        Screens.CreateFoodScreen.navigate(navController!!)
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color.White
                        )
                    )
                }
            }, floatingActionButtonPosition = FabPosition.End
        ) {
            when(foodResponse.status){
                StatusTypes.Loading->{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }
                StatusTypes.Success->{
                    LazyColumn(
                        state = listState,
                        content = {
                            for (i in foodResponse.data!!) {
                                item {
                                    FoodItemCell(i) {
                                        Screens.FoodDetailScreen.navigate(
                                            navController = navController!!,
                                            foodItem = i
                                        )
                                        /* foodItemDetails=i
                                         navController?.navigate("foodDetails")*/
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize().padding(top = 10.dp)
                    )
                }
                StatusTypes.Error->{
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No Data Found.!")
                    }
                }
                StatusTypes.Nothing->{}
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FoodItemCell(
    foodItem: FoodItem? = null,
    onItemClick: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 5.dp, start = 10.dp, end = 10.dp)
        .clickable {
            onItemClick()
        }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
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
                        "Rs ${foodItem?.foodPrice?.toString() ?: "10"}",
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .width(170.dp),
                        textAlign = TextAlign.End
                    )

                }

            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun FilterMenu(expanded: MutableState<Boolean>, selectedFilter: MutableState<String>) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {

        DropdownMenuItem(onClick = {
            expanded.value = false
            selectedFilter.value = "A-Z"
        }) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedFilter.value == "A-Z", onClick = {
                    expanded.value = false
                    selectedFilter.value = "A-Z"
                })
                Text(
                    "A-Z",
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        DropdownMenuItem(onClick = {
            expanded.value = false
            selectedFilter.value = "Z-A"
        }) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedFilter.value == "Z-A", onClick = {
                    expanded.value = false
                    selectedFilter.value = "Z-A"
                })
                Text(
                    "Z-A",
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Divider()

        DropdownMenuItem(onClick = {
            expanded.value = false
            selectedFilter.value = "A-Z"
        }) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Reset",
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit(15f, TextUnitType.Sp),
                    fontWeight = FontWeight.SemiBold
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
        //ShowFoodListing(LocalContext.current)
        /*   FoodItemCell {

           }*/
    }
}