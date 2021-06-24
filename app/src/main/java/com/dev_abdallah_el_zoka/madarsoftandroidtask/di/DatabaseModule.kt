package com.dev_abdallah_el_zoka.madarsoftandroidtask.di

import android.content.Context
import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.room.ApplicantsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabaseInstance(@ApplicationContext context: Context): ApplicantsRoomDatabase {
        return ApplicantsRoomDatabase.getInstance(context)
    }
}