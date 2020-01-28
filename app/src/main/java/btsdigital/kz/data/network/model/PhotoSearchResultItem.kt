package btsdigital.kz.data.network.model

import btsdigital.kz.data.network.model.PhotoMedia
import com.google.gson.annotations.SerializedName
import java.util.*

class PhotoSearchResultItem(
        val title: String,
        val link: String,
        val media: PhotoMedia,
        @SerializedName("date_taken") val dateTaken: Date,
        @SerializedName("published") val datePublished: Date) {

    val url: String
        get() = media.url

    override fun toString(): String {
        return "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", media=" + media +
                ", dateTaken=" + dateTaken +
                ", datePublished=" + datePublished
    }
}
