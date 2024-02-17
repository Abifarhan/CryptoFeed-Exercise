package com.hightech.cryptoapp.crypto.feed.http.infrastructure

import android.media.session.MediaSessionManager.RemoteUserInfo
import com.hightech.cryptoapp.crypto.feed.http.api.ConnectivityException
import com.hightech.cryptoapp.crypto.feed.http.api.CryptoFeedHttpClient
import com.hightech.cryptoapp.crypto.feed.http.api.HttpClientResult
import com.hightech.cryptoapp.crypto.feed.http.api.InvalidDataException
import com.hightech.cryptoapp.crypto.feed.http.api.RemoteCoinInfoAPI
import com.hightech.cryptoapp.crypto.feed.http.api.RemoteDisplayAPI
import com.hightech.cryptoapp.crypto.feed.http.api.RemoteRootCryptoFeed
import com.hightech.cryptoapp.crypto.feed.http.api.RemoteUsdAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class CryptoFeedRetrofitHttpClient constructor(
    private val cryptoFeedService: CryptoFeedService
): CryptoFeedHttpClient {
    override fun get(): Flow<HttpClientResult> = flow {
        try {
            val result  = cryptoFeedService.get()
            val fromInfraToAppLogic = RemoteRootCryptoFeed(
                data = result.data.map {
                    com.hightech.cryptoapp.crypto.feed.http.api.RemoteCryptoFeedItemAPI(
                        remoteCoinInfo = RemoteCoinInfoAPI(
                            id = it.remoteCoinInfo.id,
                            name = it.remoteCoinInfo.name,
                            fullName = it.remoteCoinInfo.fullName,
                            imageUrl = it.remoteCoinInfo.imageUrl
                        ),
                        remoteRaw = RemoteDisplayAPI(
                            usd = RemoteUsdAPI(
                                price = it.remoteRaw.usd.price,
                                changePctDay = it.remoteRaw.usd.changePctDay)
                            )

                    )
                }
                )
            emit(HttpClientResult.Success(fromInfraToAppLogic))
        } catch (throwable: Throwable) {
            when(throwable) {
                is IOException -> {
                    emit(HttpClientResult.Failure(ConnectivityException()))
                }
                is HttpException -> {
                    if (throwable.code() == 422) {
                        emit(HttpClientResult.Failure(InvalidDataException()))
                    }
                }
                else -> {
                    emit(HttpClientResult.Failure(InvalidDataException()))
                }
            }
        }
    }.flowOn(Dispatchers.IO)


}