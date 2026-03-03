package com.example.onestop.home.quranQuote

class QuranRepository(private val api: QuranApiService) {

    suspend fun getRandomAyah(): Result<Pair<String, String>> {
        return try {
            val random = (1..6236).random()
            val response = api.getAyah(random)
            if (response.isSuccessful) {
                val body = response.body()!!
                val arabic = body.data[0].text
                val translation = body.data[1].text
                Result.success(Pair(arabic, translation))
            } else {
                Result.failure(Exception("API Error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}