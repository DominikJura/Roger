package pl.jurassic.roger.util.injection.modules

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import pl.jurassic.roger.util.database.WorkTimeDao
import pl.jurassic.roger.util.database.WorkTimeDatabase
import pl.jurassic.roger.util.repository.Repository
import pl.jurassic.roger.util.repository.RepositoryImpl
import pl.jurassic.roger.util.tools.DateFormatter

@Module
class DatabaseModule {

    companion object {
        private const val DATABASE_NAME = "recipes-db"
    }

    @Provides
    fun workTimeDatabase(context: Context): WorkTimeDatabase =
            Room.databaseBuilder(
                    context,
                    WorkTimeDatabase::class.java,
                    DATABASE_NAME
            ).allowMainThreadQueries().build()

    @Provides
    fun workTimeDao(database: WorkTimeDatabase) =
            database.workTimeDao()

    @Provides
    fun repository(workTimeDao: WorkTimeDao, dateFormatter: DateFormatter): Repository =
            RepositoryImpl(workTimeDao, dateFormatter)
}