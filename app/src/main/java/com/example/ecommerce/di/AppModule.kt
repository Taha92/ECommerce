package com.example.ecommerce.di


import com.example.ecommerce.repository.FireRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFireBookRepository()
            = FireRepository(queryProduct = FirebaseFirestore.getInstance()
        .collection("products"))
}