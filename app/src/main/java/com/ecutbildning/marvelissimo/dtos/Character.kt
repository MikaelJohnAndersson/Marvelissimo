package com.ecutbildning.marvelissimo.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character  (
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comics : ContentInfoList,
    val series : ContentInfoList,
    val events : ContentInfoList,
    val stories : ContentInfoList
): Parcelable