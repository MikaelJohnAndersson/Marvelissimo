package com.ecutbildning.marvelissimo.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumbnail(
    val path: String,
    val extension: String
) : Parcelable {
    fun getUrl(): String {
        return "$path.$extension"
    }
}