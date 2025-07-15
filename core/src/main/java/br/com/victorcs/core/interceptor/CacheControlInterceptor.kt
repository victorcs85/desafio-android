package br.com.victorcs.core.interceptor

import br.com.victorcs.core.services.WifiService
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

private const val CACHE_CONTROL = "Cache-Control"
private const val MAX_DAYS = 10
private const val MAX_STALE = 7

class CacheControlInterceptor(private val wifiService: WifiService) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!wifiService.isOnline()) {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(MAX_STALE, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()
        }

        val response = chain.proceed(request)

        val cacheControl = CacheControl.Builder()
            .maxAge(MAX_DAYS, TimeUnit.DAYS)
            .build()

        return response.newBuilder()
            .header(CACHE_CONTROL, cacheControl.toString())
            .build()
    }
}
