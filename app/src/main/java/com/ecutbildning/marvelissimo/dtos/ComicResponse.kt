package com.ecutbildning.marvelissimo.dtos

data class ComicResponse (val code: Int,
                          val etag: String,
                          val data : ComicData)