package com.wuleizhenshang.androidmvitest.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/25 11:27
 * @Description: 网络请求工具类
 */
/**
 * 网络工具类
 */
object NetworkUtils {

    private const val BASE_URL = "http://service.picasso.adesk.com/"

    /**
     * 通过Moshi 将JSON转为为 Kotlin 的Data class
     */
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * 构建Retrofit
     */
    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    /**
     * 创建Api网络请求服务
     */
    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}
