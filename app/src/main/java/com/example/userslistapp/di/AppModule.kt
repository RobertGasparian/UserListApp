package com.example.userslistapp.di

import androidx.room.Room
import com.example.userslistapp.database.AppDatabase
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.PersonDTO
import com.example.userslistapp.models.mappers.*
import com.example.userslistapp.models.mappers.converters.UserConverter
import com.example.userslistapp.models.mappers.converters.UserConverterImpl
import com.example.userslistapp.networking.RetrofitBuilder
import com.example.userslistapp.ui.activities.MainActivity
import com.example.userslistapp.ui.navigation.MainActivityNavigator
import com.example.userslistapp.ui.navigation.Navigator
import com.example.userslistapp.usecases.*
import com.example.userslistapp.viewmodels.UserListViewModel
import com.example.userslistapp.viewmodels.UserListViewModelImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<Mapper<User, UserDBM>> {
        UserToUserDBMMapper
    }

    single<Mapper<PersonDTO, UserDBM?>> {
        PersonToUserDBMMapper
    }

    single<Mapper<UserDBM, PersonDTO>> {
        DBMToPersonMapper
    }

    single<Mapper<UserDBM, User>> {
        DBMToUserMapper
    }

    single<Mapper<PersonDTO, User?>> {
        PersonToUserMapper
    }

    single<Mapper<User, PersonDTO>> {
        UserToPersonMapper
    }

    single<UserConverter> {
        UserConverterImpl(get(), get(), get(), get(), get(), get())
    }

    factory<Navigator>(named(MainActivity::class.simpleName!!)) { params ->
        MainActivityNavigator(params.get(), params.get())
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, "users-list-database"
        ).build()
    }

    factory {
        HttpLoggingInterceptor()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        RetrofitBuilder.getRetrofit(get())
    }

    factory<GetAllUsersUseCase> {
        GetAllUsersUseCaseImpl()
    }

    factory<AddUserUseCase> {
        AddUserUseCaseImpl()
    }

    factory<DeleteUserUseCase> {
        DeleteUserUseCaseImpl()
    }

    viewModel<UserListViewModel> {
        UserListViewModelImpl(androidApplication(), get(), get(), get())
    }
}