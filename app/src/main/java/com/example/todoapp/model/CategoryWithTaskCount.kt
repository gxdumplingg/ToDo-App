package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Embedded


class CategoryWithTaskCount {
    @Embedded
    var category: Category? = null
    @ColumnInfo var taskCount: Int = 0
}