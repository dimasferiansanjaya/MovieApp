package id.dimasferians.moviecatalogue.core.data.source.remote.response

import com.squareup.moshi.Json

data class ListTvShowResponse(
    @field:Json(name = "page")
    val page: Int,

    @field:Json(name = "total_pages")
    val totalPage: Int,

    @field:Json(name = "results")
    val listTvShow: List<TvShowResponse>
)