package com.abdalla.altarifiappfinal.di

import android.content.Context
import androidx.room.Room
import com.abdalla.altarifiappfinal.db.FavouritesDao
import com.abdalla.altarifiappfinal.db.JetQuotesDatabase
import com.abdalla.altarifiappfinal.dp.UIModeDataStore
import com.abdalla.altarifiappfinal.dp.UIModeImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppComponent {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): UIModeImpl {
        return UIModeDataStore(context)
    }


    @Singleton
    @Provides
    fun provideDao(jetQuotesDatabase: JetQuotesDatabase): FavouritesDao =
        jetQuotesDatabase.getFavouritesDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): JetQuotesDatabase =
        Room.databaseBuilder(
            context,
            JetQuotesDatabase::class.java,
            "favourites-dbApp"
        ).fallbackToDestructiveMigration().build()

}