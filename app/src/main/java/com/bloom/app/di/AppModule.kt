package com.bloom.app.di

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides @Singleton
    fun provideAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Provides @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()


    @Provides @Singleton
    fun providePlantRepository(
        firestore: FirebaseFirestore
    ): PlantRepository = PlantRepository(firestore)
}