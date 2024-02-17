package com.hightech.cryptoapp.crypto.feed.http.api


data class RemoteRootCryptoFeed(
    val data: List<RemoteCryptoFeedItemAPI>
)

data class RemoteCryptoFeedItemAPI(
    val remoteCoinInfo: RemoteCoinInfoAPI,
    val remoteRaw: RemoteDisplayAPI
)

data class RemoteCoinInfoAPI(
    val id: String?,
    val name: String?,
    val fullName: String?,
    val imageUrl: String?,
)

data class RemoteDisplayAPI(
    val usd: RemoteUsdAPI
)

data class RemoteUsdAPI(
    val price: Double?,
    val changePctDay: Float?
)