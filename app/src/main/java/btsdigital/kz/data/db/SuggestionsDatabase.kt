package btsdigital.kz.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import btsdigital.kz.utils.AppConstants
import malmalimet.kz.data.db.DbSuggestions
import malmalimet.kz.data.db.SuggestionsDao

@Database(entities = [DbSuggestions::class], version = AppConstants.DB_VERSION)
abstract class SuggestionsDatabase : RoomDatabase() {

    abstract fun suggestionsDao(): SuggestionsDao

    companion object {

        @Volatile
        private var INSTANCE: SuggestionsDatabase? = null

        fun getInstance(context: Context): SuggestionsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                        SuggestionsDatabase::class.java,
                        AppConstants.DB_NAME)
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
