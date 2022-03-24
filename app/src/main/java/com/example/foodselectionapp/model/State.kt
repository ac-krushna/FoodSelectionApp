package com.example.foodselectionapp.model

enum class StatusTypes{
    Loading,Error,Success,Nothing
}
data class State<T>(
    var status:StatusTypes,
    var message:String?=null,
    var data :T?=null
)
