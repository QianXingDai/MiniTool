package com.kakacat.minitool.common.base

interface IView<T : IPresenter?> : IContext {
    fun initData()
    fun initView()
}