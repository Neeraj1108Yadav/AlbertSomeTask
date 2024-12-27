package com.example.albertsome_task.di

import com.example.albertsome_task.listener.RandomUserListener
import com.example.albertsome_task.repo.UserRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
abstract class UserRepoModule {

    @Singleton
    @Binds
    abstract fun bindUserRepoModule(userRepo: UserRepo): RandomUserListener
}