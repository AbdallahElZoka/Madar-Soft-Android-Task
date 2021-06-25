package com.dev_abdallah_el_zoka.madarsoftandroidtask.di

import com.dev_abdallah_el_zoka.madarsoftandroidtask.model.room.ApplicantsRoomDatabase
import com.dev_abdallah_el_zoka.madarsoftandroidtask.repo.ApplicantsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideApplicantRepository(roomDatabase: ApplicantsRoomDatabase): ApplicantsRepository {
        return ApplicantsRepository(roomDatabase = roomDatabase)
    }
}