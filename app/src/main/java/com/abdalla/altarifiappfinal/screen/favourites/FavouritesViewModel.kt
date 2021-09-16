package com.abdalla.altarifiappfinal.screen.favourites

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdalla.altarifiappfinal.db.MainRepository
import com.abdalla.altarifiappfinal.db.Model
import com.abdalla.altarifiappfinal.utils.FavouriteViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel
@Inject constructor
    (private val repository: MainRepository) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _favState = MutableStateFlow<FavouriteViewState>(FavouriteViewState.Loading)

    // UI collects from this StateFlow to get it's state update
    val favState = _favState.asStateFlow()


    // get all favourites
    init {
        viewModelScope.launch {
            repository.getAllFavourites().distinctUntilChanged().collect { result ->
                if (result.isNullOrEmpty()) {
                    _favState.value = FavouriteViewState.Empty
                } else {
                    _favState.value = FavouriteViewState.Success(result)
                }
            }
        }
    }


    // delete favourite
    fun deleteFavourite(model: Model) = viewModelScope.launch {
        repository.delete(model)
    }


    private var _onBitmapCreated = MutableLiveData<Bitmap?>(null)
    var onBitmapGenerated: MutableLiveData<Bitmap?> = _onBitmapCreated

   fun bitmapCreated(bitmap: Bitmap?) {
        _onBitmapCreated.value = bitmap
    }
}