package com.uasproject.footballs.event.presenter

import com.google.gson.Gson
import com.uasproject.footballs.api.ApiRepository
import com.uasproject.footballs.api.DBApi
import com.uasproject.footballs.team.model.TeamResponse
import com.uasproject.footballs.util.CoroutineContextProvider
import com.uasproject.footballs.event.interfaces.EventDetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventDetailPresenter(
    private val view: EventDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getHomeTeam(homeTeamId: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(DBApi.getTeamDetail(homeTeamId)).await(),
                TeamResponse::class.java
            )
            if (data.teams.isNotEmpty()){
                view.dataHome(data.teams[0])
            }
        }
    }

    fun getAwayTeam(awayTeamId: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(DBApi.getTeamDetail(awayTeamId)).await(),
                TeamResponse::class.java
            )
            if(data.teams.isNotEmpty()){
                view.dataAway(data.teams[0])
            }
        }
        view.hideLoading()
    }
}
