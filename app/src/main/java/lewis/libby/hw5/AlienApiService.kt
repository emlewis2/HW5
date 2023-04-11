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
//    @GET("/aliens/5.json")
//    Log.d("Path", )
//    @GET("/johncodeos-blog/ParseJSONRetrofitConvertersExample/main/array.json")
    @GET("/aliens/{n}.json")
    suspend fun getAliens(@Path("n") n: Int): Response<List<UfoPosition>>

//    companion object {
//        fun create() =
//            Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(BASE_URL)
//                .build()
//                .create(AlienApiService::class.java)
//    }
}