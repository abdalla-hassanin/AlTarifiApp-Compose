package com.abdalla.altarifiappfinal.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class Model(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "quote")
    val quote: String = "",

    @ColumnInfo(name = "title")
    val title: String = "",


    )