package com.example.bervageorder.domain.di

import com.example.bervageorder.domain.usecase.GetMenuListUseCase
import com.example.bervageorder.domain.usecase.GetMenuListUseCaseImpl
import com.example.bervageorder.domain.usecase.GetMenuUseCase
import com.example.bervageorder.domain.usecase.GetMenuUseCaseImpl
import com.example.bervageorder.domain.usecase.GetOrderMenuUseCase
import com.example.bervageorder.domain.usecase.GetOrderMenuUseCaseImpl
import com.example.bervageorder.domain.usecase.SetOptionListUseCase
import com.example.bervageorder.domain.usecase.SetOptionListUseCaseImpl
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
        getMenuListUseCaseImpl: GetMenuListUseCaseImpl,
    ): GetMenuListUseCase

    @Singleton
    @Binds
    abstract fun bindGetMenuUseCase(
        getMenuUseCaseImpl: GetMenuUseCaseImpl,
    ): GetMenuUseCase

    @Singleton
    @Binds
    abstract fun bindSetOptionListUseCase(
        setOptionListUseCaseImpl: SetOptionListUseCaseImpl,
    ): SetOptionListUseCase

    @Singleton
    @Binds
    abstract fun bindGetOrderMenuUseCase(
        getOrderMenuUseCaseImpl: GetOrderMenuUseCaseImpl,
    ): GetOrderMenuUseCase
}
