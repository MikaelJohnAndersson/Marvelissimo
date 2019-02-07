package com.ecutbildning.marvelissimo.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumbnail(
    val path: String,
    val extension: String
) : Parcelable {
    fun getUrl(size : String): String {
        return "$path/$size.$extension"
    }
    companion object {
        const val PORTRAIT_MEDIUM = "portrait_medium"
        const val LANDSCAPE_MEDIUM = "landscape_medium"
    }
}