package com.example.onestop.home.quranQuote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiService {
    @GET("ayah/{number}/editions/quran-uthmani,en.asad")
    suspend fun getAyah(@Path("number") number: Int): Response<AyahResponse>
}