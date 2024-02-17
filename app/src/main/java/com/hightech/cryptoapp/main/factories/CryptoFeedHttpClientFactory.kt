package com.hightech.cryptoapp.main.factories

import com.hightech.cryptoapp.crypto.feed.http.api.CryptoFeedHttpClient
import com.hightech.cryptoapp.crypto.feed.http.infrastructure.CryptoFeedRetrofitHttpClient

class CryptoFeedHttpClientFactory {
    companion object {
        fun createCryptoFeedHttpClient(): CryptoFeedHttpClient {
            return CryptoFeedRetrofitHttpClient(
                CryptoFeedServiceFactory.createCryptoFeedService()
            )
        }
    }
}