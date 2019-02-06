package com.ecutbildning.marvelissimo.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContentInfoList(
    val available : Int,
    val collectionURI : String,
    val items : List<ContentInfoItem>
): Parcelable
