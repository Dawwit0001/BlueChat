package winged.example.bluechat.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import winged.example.bluechat.di.navigation.NavControllerModule
import winged.example.core_data.di.DataModule

@Module(includes = [DataModule::class, NavControllerModule::class])
@InstallIn(SingletonComponent::class)
object AppModule
