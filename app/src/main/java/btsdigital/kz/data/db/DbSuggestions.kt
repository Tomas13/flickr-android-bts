package malmalimet.kz.data.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "suggestions")
data class DbSuggestions(
        @PrimaryKey
        val suggestion: String
)
