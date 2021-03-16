package com.picsum.viewer.di

import com.picsum.viewer.data.source.local.LocalDataSource
import com.picsum.viewer.data.source.local.PicsumLocalDataSource
import com.picsum.viewer.data.source.remote.PicsumRemoteDataSource
import com.picsum.viewer.data.source.remote.RemoteDataSource
import com.picsum.viewer.data.source.repository.DefaultPicsumRepository
import com.picsum.viewer.data.source.repository.PicsumRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {
    @Binds
    abstract fun providesRemoteDataSource(
        weatherRemoteDataSourceImpl: PicsumRemoteDataSource
    ): RemoteDataSource

    @Binds
    abstract fun providesRepository(
        repositoryImpl: DefaultPicsumRepository
    ): PicsumRepository

    @Binds
    abstract fun providesLocalDataSource(
        repositoryImpl: PicsumLocalDataSource
    ): LocalDataSource
}