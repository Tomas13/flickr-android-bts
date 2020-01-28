package btsdigital.kz.data

import btsdigital.kz.data.network.ApiHelper
import btsdigital.kz.data.network.model.PhotoSearchResult
import io.reactivex.Observable
import io.reactivex.Single
import malmalimet.kz.data.db.DbSuggestions
import malmalimet.kz.data.db.SuggestionsDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
        private val suggestionsDao: SuggestionsDao,
        private val mApiHelper: ApiHelper) : ApiHelper, Repo {

    fun getSuggestions(): Single<List<DbSuggestions>> {
        return suggestionsDao.allSuggestions
    }

    fun saveSuggestions(query: String) {
        suggestionsDao.insert(DbSuggestions(query))
    }

    override fun listPhotos(): Single<PhotoSearchResult> {
        return mApiHelper.listPhotos()
    }

    override fun searchPhotos(query: String): Single<PhotoSearchResult> {
        return mApiHelper.searchPhotos(query)
    }

}