package com.wuleizhenshang.androidmvitest.network

import com.wuleizhenshang.androidmvitest.data.model.Wallpaper
import retrofit2.http.GET

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/25 11:27
 * @Description: 网络请求接口
 */
interface ApiService {

    /**
     * 获取壁纸
     */
    @GET("v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    suspend fun getWallPaper(): Wallpaper
}
