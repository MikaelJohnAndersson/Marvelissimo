package com.ecutbildning.marvelissimo.dtos

data class Response(
    val code: Int,
    val etag: String,
    val data : Data
)