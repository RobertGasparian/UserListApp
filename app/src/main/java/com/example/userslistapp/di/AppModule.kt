package com.example.userslistapp.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.userslistapp.database.AppDatabase
import com.example.userslistapp.misc.UserCreationValidator
import com.example.userslistapp.misc.UserCreationValidatorImpl
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.PersonDTO
import com.example.userslistapp.models.mappers.*
import com.example.userslistapp.models.mappers.converters.UserConverter
import com.example.userslistapp.models.mappers.converters.UserConverterImpl
import com.example.userslistapp.networking.ApiService
import com.example.userslistapp.networking.NetworkService
import com.example.userslistapp.networking.NetworkServiceImpl
import com.example.userslistapp.networking.RetrofitBuilder
import com.example.userslistapp.repositories.UserRepo
import com.example.userslistapp.repositories.UserRepoImpl
import com.example.userslistapp.ui.activities.MainActivity
import com.example.userslistapp.ui.navigation.MainActivityNavigator
import com.example.userslistapp.ui.navigation.Navigator
import com.example.userslistapp.usecases.*
import com.example.userslistapp.viewmodels.AddUserVIewModel
import com.example.userslistapp.viewmodels.AddUserVIewModelImpl
import com.example.userslistapp.viewmodels.UserListViewModel
import com.example.userslistapp.viewmodels.UserListViewModelImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module(override = true) {
    single<Mapper<User, UserDBM>>(named(MODEL_TO_DBM)) {
        UserToUserDBMMapper
    }

    single<Mapper<PersonDTO, UserDBM?>>(named(DTO_TO_DBM)) {
        PersonToUserDBMMapper
    }

    single<Mapper<UserDBM, PersonDTO>>(named(DBM_TO_DTO)) {
        DBMToPersonMapper
    }

    single<Mapper<UserDBM, User>>(named(DBM_TO_MODEL)) {
        DBMToUserMapper
    }

    single<Mapper<PersonDTO, User?>>(named(DTO_TO_MODEL)) {
        PersonToUserMapper
    }

    single<Mapper<User, PersonDTO>>(named(MODEL_TO_DTO)) {
        UserToPersonMapper
    }

    single<UserConverter> {
        UserConverterImpl(
            get(named(DTO_TO_MODEL)),
            get(named(DTO_TO_DBM)),
            get(named(MODEL_TO_DBM)),
            get(named(MODEL_TO_DTO)),
            get(named(DBM_TO_MODEL)),
            get(named(DBM_TO_DTO))
        )
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

    factory {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single<NetworkService> {
        NetworkServiceImpl(get())
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
        UserListViewModelImpl(get(), get(), get())
    }

    viewModel<AddUserVIewModel> {
        AddUserVIewModelImpl(get())
    }

    factory<UserRepo> {
        UserRepoImpl(get(), get(), get(), get())
    }

    single<UserCreationValidator> {
        UserCreationValidatorImpl
    }
}

const val DTO_TO_MODEL = "dto_to_model"
const val DTO_TO_DBM = "dto_to_dbm"
const val DBM_TO_MODEL = "dbm_to_model"
const val DBM_TO_DTO = "dbm_to_dto"
const val MODEL_TO_DTO = "model_to_dto"
const val MODEL_TO_DBM = "model_to_dbm"