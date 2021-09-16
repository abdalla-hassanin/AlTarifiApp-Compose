package com.abdalla.altarifiappfinal.utils

import com.abdalla.altarifiappfinal.db.Model

sealed class FavouriteViewState {

    // Represents different states for quotes
    object Empty : FavouriteViewState()
    object Loading : FavouriteViewState()
    data class Success(val quote: List<Model>) : FavouriteViewState()
    data class Error(val exception: Throwable) : FavouriteViewState()

}