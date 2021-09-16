package com.abdalla.altarifiappfinal.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): Flow<List<Model>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(model: Model)

    @Delete
    suspend fun delete(model: Model)


}