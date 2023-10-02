package com.example.bervageorder.domain.di

import com.example.bervageorder.domain.usecase.GetMenuListUseCase
import com.example.bervageorder.domain.usecase.GetMenuListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindGetMenuListUseCase(
        getMenuListUseCaseImpl: GetMenuListUseCaseImpl
    ): GetMenuListUseCase
}