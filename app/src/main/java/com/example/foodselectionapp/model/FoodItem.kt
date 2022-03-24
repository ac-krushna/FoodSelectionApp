package com.example.foodselectionapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FoodItem")
data class FoodItem(
    @PrimaryKey
    var foodId: String,
    @ColumnInfo(name = "foodName")
    public var foodName:String?="",
    @ColumnInfo(name = "foodPrice")
    var foodPrice:String?="0",
    @ColumnInfo(name = "foodBrand")
    var foodBrand:String?=""
)