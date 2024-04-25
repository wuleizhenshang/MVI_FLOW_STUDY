package com.wuleizhenshang.androidmvitest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wuleizhenshang.androidmvitest.data.repository.MainRepository
import com.wuleizhenshang.androidmvitest.network.ApiService

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/25 11:47
 * @Description: ViewModel工厂
 */
/**
 * ViewModel工厂
 */
class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 判断 MainViewModel 是不是 modelClass 的父类或接口
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiService)) as T
        }
        throw IllegalArgumentException("UnKnown class")
    }
}