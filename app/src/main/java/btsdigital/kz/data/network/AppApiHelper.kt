package btsdigital.kz.data.network

import btsdigital.kz.data.network.model.PhotoSearchResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor(val networkService: NetworkService) : ApiHelper {

    override fun listPhotos(): Single<PhotoSearchResult> {
        return networkService.listPhotos()
    }

    override fun searchPhotos(query: String): Single<PhotoSearchResult> {
        val formattedQuery = query.replace("/[,\\s]/".toRegex(), ",")
        return networkService.searchPhotos(formattedQuery)
    }
}