package com.abdalla.altarifiappfinal.screen.detail

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdalla.altarifiappfinal.db.Model
import com.abdalla.altarifiappfinal.db.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor
    (private val repository: MainRepository) : ViewModel() {
    // insert favourite
    fun insertFavourite(model: Model) = viewModelScope.launch {
        repository.insert(model)
    }

    private var _onBitmapCreated = MutableLiveData<Bitmap?>(null)
    var onBitmapGenerated: MutableLiveData<Bitmap?> = _onBitmapCreated

    fun bitmapCreated(bitmap: Bitmap?) {
        _onBitmapCreated.value = bitmap
    }
}
