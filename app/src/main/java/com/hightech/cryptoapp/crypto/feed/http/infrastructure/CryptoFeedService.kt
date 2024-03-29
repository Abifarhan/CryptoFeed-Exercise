package com.hightech.cryptoapp.crypto.feed.http.infrastructure

import com.hightech.cryptoapp.crypto.feed.http.api.RemoteRootCryptoFeed
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoFeedService {
    @GET("data/top/totaltoptiervolfull")
    suspend fun get(
        @Query("limit") limit: Int? = 20,
        @Query("tsym") tsym: String? = "USD"
    ): CryptoFeedResponse
}