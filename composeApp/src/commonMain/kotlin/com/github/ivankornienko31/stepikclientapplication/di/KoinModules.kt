package com.github.ivankornienko31.stepikclientapplication.di

import com.github.ivankornienko31.stepikclientapplication.datastore.AuthPreferences
import com.github.ivankornienko31.stepikclientapplication.datastore.OnboardingPreferences
import com.github.ivankornienko31.stepikclientapplication.screens.auth.data.AuthRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.repository.AuthRepository
import com.github.ivankornienko31.stepikclientapplication.screens.auth.domain.usecase.AuthLoginUseCase
import com.github.ivankornienko31.stepikclientapplication.screens.auth.presentation.AuthScreenViewModel
import com.github.ivankornienko31.stepikclientapplication.screens.login.data.LoginRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.screens.login.domain.LoginRepository
import com.github.ivankornienko31.stepikclientapplication.screens.login.domain.LoginUseCase
import com.github.ivankornienko31.stepikclientapplication.screens.login.presentation.LoginViewModel
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.StepikCoursesRepositoryImpl
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.stepikHttpClient
import com.github.ivankornienko31.stepikclientapplication.screens.main.domain.StepikCoursesRepository
import com.github.ivankornienko31.stepikclientapplication.screens.main.presentation.StepikMainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.bind

val networkModule = module {
    // Koin создаст HttpClient один раз (single) и будет везде его переиспользовать
    single {
        stepikHttpClient(
            authPreferences = get()
        )
    }
}

val dataModule = module {
    // Связываем интерфейсы с их реализациями
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    singleOf(::LoginRepositoryImpl) bind LoginRepository::class
    singleOf(::StepikCoursesRepositoryImpl) bind StepikCoursesRepository::class
}

val preferencesModule = module {
    singleOf(::OnboardingPreferences)
    singleOf(::AuthPreferences)
}

val domainModule = module {
    factoryOf(::AuthLoginUseCase) // factory - создает новый экземпляр при каждом запросе
    factoryOf(::LoginUseCase) // factory - создает новый экземпляр при каждом запросе
}

val viewModelModule = module {
    // Регистрируем ViewModels (используем viewModelOf из koin-compose-viewmodel)
    viewModelOf(::AuthScreenViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::StepikMainViewModel)
}

val sharedModules = listOf(
    networkModule,
    dataModule,
    preferencesModule,
    domainModule,
    viewModelModule,
)