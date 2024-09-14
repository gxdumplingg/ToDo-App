package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(
    tableName = "task_table",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["category_id"], // bảng Category
        childColumns = ["categoryId"], // bảng Task
        onDelete = ForeignKey.CASCADE // bảng cha xóa thì các hàng liên quan trong bảng con cũng xóa theo
    )]
)
data class Task (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId") val id : Int,
    @ColumnInfo(name = "taskTitle") val title : String,
    @ColumnInfo val dueDate : String,
    @ColumnInfo val timeStart : String,
    @ColumnInfo val timeEnd : String,
    @ColumnInfo val categoryId : Int,
    @ColumnInfo var status : Int,
    @ColumnInfo var priority : Int,
    @ColumnInfo val description : String,
    @ColumnInfo var isArchived : Boolean,
    @ColumnInfo var isDone : Boolean,
)