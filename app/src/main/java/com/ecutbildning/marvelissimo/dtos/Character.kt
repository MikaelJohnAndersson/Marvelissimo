package com.ecutbildning.marvelissimo.dtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Character  (
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
): Parcelable