package lewis.libby.hw5

import android.util.Log
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

//const val BASE_URL = "http://javadude.com"

interface AlienApiService {
    @GET("/aliens/{n}.json")
    suspend fun getAliens(@Path("n") n: Int): Response<List<UfoPosition>>
}