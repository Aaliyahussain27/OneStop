package com.example.onestop.home.quranQuote

data class AyahResponse(
    val data: List<AyahData>
)

data class AyahData(
    val text: String,
    val edition: Edition
)

data class Edition(
    val identifier: String,
    val language: String
)