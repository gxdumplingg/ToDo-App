package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id") val id : Int,
    @ColumnInfo(name = "category_title") var title : String,
    @ColumnInfo var completedPercent : Int)
