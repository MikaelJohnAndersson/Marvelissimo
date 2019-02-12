package com.ecutbildning.marvelissimo.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContentInfoItem (
    val resourceURI : String,
    val name : String
): Parcelable
