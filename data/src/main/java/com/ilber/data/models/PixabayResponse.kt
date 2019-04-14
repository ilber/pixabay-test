package com.ilber.data.models

class PixabayResponse(
    val hits: List<Hit>
) {

    class Hit(
        val previewURL: String?,
        val tags: String?,
        val user: String
    )
}