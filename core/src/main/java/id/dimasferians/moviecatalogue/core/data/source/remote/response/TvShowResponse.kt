package id.dimasferians.moviecatalogue.core.data.source.remote.response

import com.squareup.moshi.Json

data class TvShowResponse(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "original_name")
    val name: String,

    @field:Json(name = "overview")
    val overview: String?,

    @field:Json(name = "poster_path")
    val image: String?,

    @field:Json(name = "vote_average")
    val voteAverage: Double,

    @field:Json(name = "vote_count")
    val voteCount: Int
)