package com.hussein.suggesttest.util.webservicehelper

import com.hussein.suggesttest.model.Restaurant
import com.hussein.suggesttest.util.apphelper.AppConfigHelper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpointInterface {
    @GET(AppConfigHelper.SUGGEST_URL)
    fun getSuggestRestaurant(@Query("uid") uid:String, @Query("get_param") get_param:String): Observable<Restaurant>
}
