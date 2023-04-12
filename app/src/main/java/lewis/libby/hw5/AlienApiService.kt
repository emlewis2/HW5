package lewis.libby.hw5

// Utilized Examples by Scott Stanchfield

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlienApiService {
    @GET("/aliens/{n}.json")
    suspend fun getAliens(@Path("n") n: Int): Response<List<UfoPosition>>
}