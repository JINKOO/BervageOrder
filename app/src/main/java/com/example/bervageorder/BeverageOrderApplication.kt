package com.example.bervageorder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

// Application 구분해서 작성 / 새로운 디렉토리 만들어사 debug / release 요줌에는 initializer
// OR Initializer
/**
 *  BuildVariant 구성 방법
 *   - 간단한 경우 : gradle 파일에서 buildFlavor를 사용한 변경
 *   - 복잡한 경우 : project구조에서 Application 클래스를 release, debug로 따로 만든다.
 */
@HiltAndroidApp
open class BeverageOrderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}