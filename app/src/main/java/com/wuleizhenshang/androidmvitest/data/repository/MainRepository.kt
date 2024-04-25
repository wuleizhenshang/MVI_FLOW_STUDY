package com.wuleizhenshang.androidmvitest.data.repository

import com.wuleizhenshang.androidmvitest.network.ApiService

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/25 11:39
 * @Description: TODO
 */
/**
 * 数据存储库
 */
class MainRepository(private val apiService: ApiService) {

    /**
     * 获取壁纸
     */
    suspend fun getWallPaper() = apiService.getWallPaper()
}