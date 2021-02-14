// package com.onepos.posandroidv2.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "category")
@JsonClass(generateAdapter = true)
data class Category(
    @PrimaryKey var id: Int?,
    @Json(name = "category_name")var name:String, 
    var is_service:Boolean,
    var is_active:Boolean,
    var is_delete:Boolean
)


// Use for add data at initiate
//{
//    companion object{
//        fun populate()= arrayOf(
//            Category(1,"Soup",true,false),
//            Category(2,"Brush",true,false)
//        )
//    }
//}