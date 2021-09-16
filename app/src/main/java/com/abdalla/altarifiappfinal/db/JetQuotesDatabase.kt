
package com.abdalla.altarifiappfinal.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Model::class], version = 1, exportSchema = false)
abstract class JetQuotesDatabase : RoomDatabase() {
    abstract fun getFavouritesDao(): FavouritesDao
}