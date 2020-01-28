package malmalimet.kz.data.db

import android.arch.persistence.room.*
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface SuggestionsDao {

    @get:Query("SELECT * FROM suggestions")
    val allSuggestions: Single<List<DbSuggestions>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg suggestions: DbSuggestions)

    @Delete
    fun delete(vararg suggestions: DbSuggestions)

    @Query("DELETE FROM suggestions")
    fun clearSuggestions()
}
