package com.abdalla.altarifiappfinal.screen.quoteslist

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdalla.altarifiappfinal.db.MainRepository
import com.abdalla.altarifiappfinal.db.Model
import com.abdalla.altarifiappfinal.model.Quote
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesListViewModel
@Inject constructor(private val repository: MainRepository) : ViewModel() {
    // Backing property to avoid state updates from other classes
    private val _getQuotes = MutableStateFlow<List<Quote>>(emptyList())

    // UI collects from this StateFlow to get it's state update
    val getQuotes =_getQuotes.asStateFlow()


    private var _onBitmapCreated = MutableLiveData<Bitmap?>(null)
    var onBitmapGenerated: MutableLiveData<Bitmap?> = _onBitmapCreated

    fun bitmapCreated(bitmap: Bitmap?) {
        _onBitmapCreated.value = bitmap
    }

    // insert favourite
    fun insertFavourite(model: Model) = viewModelScope.launch {
        repository.insert(model)
    }



    // get all quotes from assets folder
    fun getAllQuotes(context: Context) = viewModelScope.launch {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listType = Types.newParameterizedType(List::class.java, Quote::class.java)
        val adapter: JsonAdapter<List<Quote>> = moshi.adapter(listType)
        val myJson =
            context.assets.open("quotes.json").bufferedReader().use { it.readText() }
        _getQuotes.value = adapter.fromJson(myJson)!!

    }


}