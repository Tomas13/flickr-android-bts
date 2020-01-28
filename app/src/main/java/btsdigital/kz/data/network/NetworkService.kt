package btsdigital.kz.data.network

import btsdigital.kz.data.network.model.PhotoSearchResult
import io.reactivex.Single
import btsdigital.kz.utils.AppConstants.SEARCH_PATH
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(SEARCH_PATH)
    fun listPhotos(): Single<PhotoSearchResult>

    @GET(SEARCH_PATH)
    fun searchPhotos(@Query("tags") tags: String): Single<PhotoSearchResult>
}