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
    var foodName:String?="",
    @ColumnInfo(name = "foodPrice")
    var foodPrice:String?="0",
    @ColumnInfo(name = "foodBrand")
    var foodBrand:String?=""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(foodId)
        parcel.writeString(foodName)
        parcel.writeString(foodPrice)
        parcel.writeString(foodBrand)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodItem> {
        override fun createFromParcel(parcel: Parcel): FoodItem {
            return FoodItem(parcel)
        }

        override fun newArray(size: Int): Array<FoodItem?> {
            return arrayOfNulls(size)
        }
    }

}
