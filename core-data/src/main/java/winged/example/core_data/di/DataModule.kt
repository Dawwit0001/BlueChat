package winged.example.core_data.di

import android.content.Context
import android.provider.Settings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// will probably be used to store messages later!
@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideDeviceName(@ApplicationContext context: Context): String {
        return Settings.Global.getString(context.contentResolver, "device_name")
    }
}
