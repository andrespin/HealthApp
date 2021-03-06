package android.andrespin.healthapp.model.di

import android.andrespin.healthapp.model.database.Database
import android.andrespin.healthapp.model.database.NoteDao
import android.andrespin.healthapp.model.repository.IRepo
import android.andrespin.healthapp.model.repository.Repo
import android.andrespin.healthapp.utils.converter.IConverter
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
class DatabaseModule {

    @Provides
    internal fun createDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(appContext, Database::class.java, "db.1")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    internal fun provideNoteDao(db: Database): NoteDao =
        db.noteDao()

    @Provides
    internal fun provideRepo(noteDao: NoteDao, converter: IConverter): IRepo =
        Repo(noteDao, converter)

}