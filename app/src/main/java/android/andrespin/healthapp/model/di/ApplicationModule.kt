package android.andrespin.healthapp.model.di

import android.andrespin.healthapp.App
import android.andrespin.healthapp.utils.converter.Converter
import android.andrespin.healthapp.utils.converter.IConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class ApplicationModule() {

    // https://svvashishtha.medium.com/using-room-with-hilt-cb57a1bc32f

//    @Provides
//    fun app(): App = App()

    @Provides
    internal fun provideConverter(): IConverter = Converter()

}