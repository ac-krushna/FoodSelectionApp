package com.example.foodselectionapp.ui.SaveFoodData

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.foodselectionapp.R
import com.example.foodselectionapp.model.FoodItem
import com.example.foodselectionapp.model.StatusTypes
import com.example.foodselectionapp.ui.Screens
import com.example.foodselectionapp.ui.theme.FoodSelectionAppTheme
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SaveFoodFlow(context: Context, navController: NavHostController? = null) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val listState = rememberLazyListState()
    val vm = viewModel<SaveFoodViewmodel>()
    val saveFoodResponse = vm._saveDataFlow.collectAsState().value

    var rememberFoodName = remember {
        mutableStateOf("")
    }
    var rememberFoodNameError = remember {
        mutableStateOf(false)
    }
    var rememberFoodPrice = remember {
        mutableStateOf("")
    }
    var rememberFoodPriceError = remember {
        mutableStateOf(false)
    }

    var rememberFoodBrand = remember {
        mutableStateOf("")
    }
    var rememberFoodBrandError = remember {
        mutableStateOf(false)
    }
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Food",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = TextUnit(18f, TextUnitType.Sp),
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                    )
                },
                actions = {

                },
                navigationIcon = {
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
                                .width(50.dp)
                                .height(50.dp)
                                .padding(top = 9.dp, bottom = 9.dp, end = 9.dp, start = 9.dp)
                                .clip(CircleShape),
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(contentAlignment = Alignment.BottomCenter) {
                Button(modifier = Modifier
                    .padding(bottom = 5.dp)
                    .fillMaxWidth()
                    .height(50.dp), onClick = {
                    if (rememberFoodName.value.isEmpty()) {
                        rememberFoodNameError.value = true

                    }
                    if (rememberFoodPrice.value.isEmpty()) {
                        rememberFoodPriceError.value = true

                    }
                    if (rememberFoodBrand.value.isEmpty()) {
                        rememberFoodBrandError.value = true
                        return@Button
                    }

                    val id = UUID.randomUUID().toString()
                    vm.saveFood(
                        context = context, foodItem = FoodItem(
                            foodId = id,
                            foodName = rememberFoodName.value,
                            foodBrand = rememberFoodBrand.value,
                            foodPrice = rememberFoodPrice.value
                        ), SaveFoodRepo()
                    )
                }) {
                    Text(text = "Save")
                }
            }
        },
    ) {

        when (saveFoodResponse.status) {
            StatusTypes.Success -> {
                Box(Modifier.fillMaxSize()) {
                    AddUI(
                        listState,
                        rememberFoodBrand,
                        rememberFoodBrandError,
                        rememberFoodName,
                        rememberFoodNameError,
                        rememberFoodPrice,
                        rememberFoodPriceError
                    )
                }
                Screens.FoodListingScreen.popBack(navController = navController!!)
            }
            StatusTypes.Loading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable { }) {
                    AddUI(
                        listState,
                        rememberFoodBrand,
                        rememberFoodBrandError,
                        rememberFoodName,
                        rememberFoodNameError,
                        rememberFoodPrice,
                        rememberFoodPriceError
                    )
                    ProgressUI()
                }
            }
            StatusTypes.Error -> {
                Box(
                    Modifier
                        .fillMaxSize()) {
                    AddUI(
                        listState,
                        rememberFoodBrand,
                        rememberFoodBrandError,
                        rememberFoodName,
                        rememberFoodNameError,
                        rememberFoodPrice,
                        rememberFoodPriceError
                    )
                    //ProgressUI()
                    LaunchedEffect(key1 = "", block ={
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Food Data Saving Error.!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    } )
                    SnackbarHost(hostState = snackbarHostState)
                }
            }
            StatusTypes.Nothing->{
                Box(
                    Modifier
                        .fillMaxSize()) {
                    AddUI(
                        listState,
                        rememberFoodBrand,
                        rememberFoodBrandError,
                        rememberFoodName,
                        rememberFoodNameError,
                        rememberFoodPrice,
                        rememberFoodPriceError
                    )
                }
            }

        }
    }
}

@Composable
fun ProgressUI() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator()
    }

}

@Composable
fun AddUI(
    listState: LazyListState,
    rememberFoodBrand: MutableState<String>,
    rememberFoodBrandError: MutableState<Boolean>,
    rememberFoodName: MutableState<String>,
    rememberFoodNameError: MutableState<Boolean>,
    rememberFoodPrice: MutableState<String>,
    rememberFoodPriceError: MutableState<Boolean>
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(bottom = 50.dp),
        content = {
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = rememberFoodName.value,
                    onValueChange = {
                        rememberFoodNameError.value = false
                        rememberFoodName.value = it
                    },
                    label = {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_fastfood_24),
                                contentDescription = "",
                                Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    .padding(2.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                            )
                            Text("Food Name", Modifier.padding(2.dp))
                        }
                    },
                    shape = RoundedCornerShape(10),
                    isError = rememberFoodNameError.value
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(9.dp)
                )
            }
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = rememberFoodPrice.value,
                    onValueChange = {
                        rememberFoodPriceError.value = false
                        rememberFoodPrice.value = it
                    },
                    label = {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_fastfood_24),
                                contentDescription = "",
                                Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    .padding(2.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                            )
                            Text("Food Price", Modifier.padding(2.dp))
                        }
                    },
                    shape = RoundedCornerShape(10),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = rememberFoodPriceError.value
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(9.dp)
                )
            }
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = rememberFoodBrand.value,
                    onValueChange = {
                        rememberFoodBrandError.value = false
                        rememberFoodBrand.value = it
                    },
                    label = {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_fastfood_24),
                                contentDescription = "",
                                Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    .padding(2.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                            )
                            Text("Brand", Modifier.padding(2.dp))
                        }
                    },
                    //shape = AbsoluteCutCornerShape(10),
                    shape = RoundedCornerShape(10),
                    isError = rememberFoodBrandError.value
                )
            }
        })
}

@Composable
@Preview(showSystemUi = true)
fun DefaultPreview() {
    FoodSelectionAppTheme {
        SaveFoodFlow(LocalContext.current)
    }
}