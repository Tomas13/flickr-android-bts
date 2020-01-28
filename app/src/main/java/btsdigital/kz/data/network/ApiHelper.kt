package btsdigital.kz.data.network

import btsdigital.kz.data.network.model.PhotoSearchResult
import io.reactivex.Single

interface ApiHelper {

    fun listPhotos(): Single<PhotoSearchResult>

    fun searchPhotos(query: String): Single<PhotoSearchResult>
}