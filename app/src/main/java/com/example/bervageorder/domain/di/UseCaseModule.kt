package com.example.bervageorder.domain.di

import com.example.bervageorder.domain.usecase.GetMenuListUseCase
import com.example.bervageorder.domain.usecase.GetMenuListUseCaseImpl
import com.example.bervageorder.domain.usecase.GetMenuUseCase
import com.example.bervageorder.domain.usecase.GetMenuUseCaseImpl
import com.example.bervageorder.domain.usecase.GetOrderMenuUseCase
import com.example.bervageorder.domain.usecase.GetOrderMenuUseCaseImpl
import com.example.bervageorder.domain.usecase.PostOptionListUseCase
import com.example.bervageorder.domain.usecase.PostOptionListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  UseCase는 모듈을 만들면 안된다.
 *  SingleTon일 필요도 없다.
 *
 *  Class만 사용 @Reusable
 *  UseCase에서도 Module은 안만든다.
 *
 *  UseCase는 싱글턴도 아니고, Activity도 아니다.
 *  Module개념은 미리 설치하는 건데, UseCase는 Module개념이아니다.
 *
 *  Module로 미리 공급을 해줄 필요가 없다.
 *  앱이 초기화 될때, Module을 만든다.
 *
 *  UseCase는 모듈을 만들지 않는다.
 *
 */
@Module
@InstallIn(SingletonComponent::class) // 앱이 죽을 때까지 다살아있다.
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindGetMenuListUseCase(
        getMenuListUseCaseImpl: GetMenuListUseCaseImpl
    ): GetMenuListUseCase

    @Singleton
    @Binds
    abstract fun bindGetMenuUseCase(
        getMenuUseCaseImpl: GetMenuUseCaseImpl
    ): GetMenuUseCase

    @Singleton
    @Binds
    abstract fun bindSetOptionListUseCase(
        setOptionListUseCaseImpl: PostOptionListUseCaseImpl
    ): PostOptionListUseCase

    @Singleton
    @Binds
    abstract fun bindGetOrderMenuUseCase(
        getOrderMenuUseCaseImpl: GetOrderMenuUseCaseImpl
    ): GetOrderMenuUseCase
}