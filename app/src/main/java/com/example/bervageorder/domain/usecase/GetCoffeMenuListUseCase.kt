package com.example.bervageorder.domain.usecase

import com.example.bervageorder.data.repository.MenuRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetCoffeMenuListUseCase @Inject constructor(
    private val menuRepository: MenuRepository,

) {
    suspend fun invoke() {
        // Login Api 요청
        // AuthRepository.login()
        // Login Toekn 저장
        // AuthRepository.saveToken() -> SaveTokenUseCase()
    }

    // usecase -> repository
    // feature -> Login -> LoginUsecase
}
