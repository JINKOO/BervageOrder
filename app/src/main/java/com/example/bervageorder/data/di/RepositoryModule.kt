package com.example.bervageorder.data.di

import com.example.bervageorder.data.repository.BeverageRepository
import com.example.bervageorder.domain.repository.BeverageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindBeverageRepository(
        beverageRepositoryImpl: BeverageRepositoryImpl
    ): BeverageRepository
}