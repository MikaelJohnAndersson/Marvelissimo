package com.ecutbildning.marvelissimo.dtos

data class CharacterDataWrapper(
    val code: Int,
    val etag: String,
    val data : CharacterDataContainer
)