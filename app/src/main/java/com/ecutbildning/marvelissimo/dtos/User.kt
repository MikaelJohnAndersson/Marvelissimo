package com.ecutbildning.marvelissimo.dtos

import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class User(
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var loggedIn: Boolean = false,
    var favoriteCharacters: MutableCollection<String>? = null,
    var favoriteComics: MutableCollection<String>? = null
)