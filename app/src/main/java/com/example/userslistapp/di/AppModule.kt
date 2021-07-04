package com.example.userslistapp.di

import androidx.room.Room
import com.example.userslistapp.database.AppDatabase
import com.example.userslistapp.database.UserDao
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.PersonDTO
import com.example.userslistapp.models.mappers.*
import com.example.userslistapp.models.mappers.converters.UserConverter
import com.example.userslistapp.models.mappers.converters.UserConverterImpl
import com.example.userslistapp.networking.ApiService
import com.example.userslistapp.networking.RetrofitBuilder
import com.example.userslistapp.repositories.UserRepo
import com.example.userslistapp.repositories.UserRepoImpl
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
import retrofit2.Retrofit

val appModule = module(override = true) {
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

    single {
        get<AppDatabase>().userDao()
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

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    factory<GetAllUsersUseCase> {
        GetAllUsersUseCaseImpl(get())
    }

    factory<AddUserUseCase> {
        AddUserUseCaseImpl(get())
    }

    factory<DeleteUserUseCase> {
        DeleteUserUseCaseImpl(get())
    }

    viewModel<UserListViewModel> {
        UserListViewModelImpl(androidApplication(), get(), get(), get())
    }

    factory<UserRepo> {
        UserRepoImpl(get(), get(), get())
    }
}