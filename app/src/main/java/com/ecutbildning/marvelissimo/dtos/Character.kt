package com.ecutbildning.marvelissimo.dtos

data class Character (
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)