package com.uasproject.footballs.league.presenter

import com.google.gson.Gson
import com.uasproject.footballs.api.ApiRepository
import com.uasproject.footballs.api.DBApi
import com.uasproject.footballs.league.model.LeagueResponse
import com.uasproject.footballs.util.CoroutineContextProvider
import com.uasproject.footballs.league.interfaces.LeagueView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeaguePresenter(
    private val view: LeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getData() {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(DBApi.getLeague()).await(),
                LeagueResponse::class.java
            )

            view.showData(data.league)
            view.hideLoading()
        }
    }
}