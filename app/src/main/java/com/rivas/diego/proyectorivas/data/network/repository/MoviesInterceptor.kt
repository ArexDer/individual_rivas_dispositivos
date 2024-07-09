package com.rivas.diego.proyectorivas.data.network.repository

import okhttp3.Interceptor
import okhttp3.Response

class MoviesInterceptor : Interceptor {

    private val key = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2OGVjOWEyMGQzODk2ZGJlMmViNTc5NjVjMjM2MzgyNiIsIm5iZiI6MTcyMDM5NDUwMS43MTA2MDQsInN1YiI6IjY2NjFkOGQ1ZTU0YjU1MjVhNjdiMzlhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.uPAbrek5qMi4wUSXKH4_j2bXNga7gzUXE89twmiAST8"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder().build()

            val headers = request.headers.newBuilder()
            headers.add("Authorization", key)

        val newRequest = request.newBuilder().url(url).headers(headers.build()).build()

        return chain.proceed(newRequest)
    }
}
