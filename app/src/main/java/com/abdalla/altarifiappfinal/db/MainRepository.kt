package com.abdalla.altarifiappfinal.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val favouritesDao: FavouritesDao) {
    fun getAllFavourites(): Flow<List<Model>> =
        favouritesDao.getAllFavourites().flowOn(Dispatchers.IO).conflate()


    suspend fun insert(model: Model) = favouritesDao.insertFavourite(model)
    suspend fun delete(model: Model) = favouritesDao.delete(model)

}