package com.ecutbildning.marvelissimo.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comic (
    val id: Int,
    val title: String,
    val description: String,
    val digitalId :Int,
    val pageCount :Int,
    val thumbnail: Thumbnail,
    var creators: ContentInfoList,
    var characters: ContentInfoList,
    var stories : ContentInfoList,
    var events : ContentInfoList
) : Parcelable