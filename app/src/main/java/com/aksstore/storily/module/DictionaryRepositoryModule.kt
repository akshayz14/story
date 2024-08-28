package com.aksstore.storily.module

import com.aksstore.storily.domain.DictionaryRepository
import com.aksstore.storily.domain.DictionaryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DictionaryRepositoryModule {

    @Binds
    abstract fun bindDictionaryRepository(dictionaryRepositoryImpl: DictionaryRepositoryImpl): DictionaryRepository


}