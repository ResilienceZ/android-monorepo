package dev.ricoferdian.resiliencez.prayitna.data.remote

import dev.ricoferdian.resiliencez.prayitna.data.remote.response.AddressResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OsmApiService {
    @GET("reverse")
    suspend fun getAddress(
        @Query("format") format: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): AddressResponse?
}