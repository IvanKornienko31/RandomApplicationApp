package com.github.ivankornienko31.stepikclientapplication.di

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
    single { stepikHttpClient() }
}

val dataModule = module {
    // Связываем интерфейсы с их реализациями
    singleOf(::LoginRepositoryImpl) bind LoginRepository::class
    singleOf(::StepikCoursesRepositoryImpl) bind StepikCoursesRepository::class
}

val domainModule = module {
    factoryOf(::LoginUseCase) // factory - создает новый экземпляр при каждом запросе
}

val viewModelModule = module {
    // Регистрируем ViewModels (используем viewModelOf из koin-compose-viewmodel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::StepikMainViewModel)
}

val sharedModules = listOf(networkModule, dataModule, domainModule, viewModelModule)