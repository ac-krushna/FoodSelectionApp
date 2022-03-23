package com.example.foodselectionapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.foodselectionapp.R

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
@Composable
public fun getHeaderTextcolor():Color{
    return colorResource(id = R.color.black)
}
@Composable
public fun getSubHeaderTextcolor():Color{
    return colorResource(id = R.color.grey)
}
@Composable
public fun getFabColor():Color{
    return colorResource(id = R.color.fab_color)
}
