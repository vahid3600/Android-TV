package ir.net_box.test.data.remote

import ir.net_box.test.data.remote.model.Playlist
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetBoxApi {
    @Headers("SESSION-KEY: 43581c49f795564442a066b11e95bcdc7dba9ac6062178d9c2fb65acce4ba761")
    @GET("playlist/1/?pageSize=10")
    suspend fun getPlaylist(@Query("page") pageNumber: Int): Playlist
}