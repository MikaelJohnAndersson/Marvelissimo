package com.ecutbildning.marvelissimo.dtos

import com.google.firebase.database.IgnoreExtraProperties
@IgnoreExtraProperties
data class User (var email: String = "",
            var firstName: String = "",
            var lastName: String = "",
            var favoriteCharacters: MutableCollection<String>? = null,
            var favoriteComics: MutableCollection<String>? = null,
            var loggedIn: Boolean = false)