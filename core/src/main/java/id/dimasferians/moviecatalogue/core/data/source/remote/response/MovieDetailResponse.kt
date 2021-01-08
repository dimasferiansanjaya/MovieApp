package id.dimasferians.moviecatalogue.core.data.source.remote.response

import com.squareup.moshi.Json

data class MovieDetailResponse(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "original_title")
    val title: String,

    @field:Json(name = "overview")
    val overview: String?,

    @field:Json(name = "poster_path")
    val image: String?,

    @field:Json(name = "backdrop_path")
    val backdropImage: String?,

    @field:Json(name = "vote_average")
    val voteAverage: Double,

    @field:Json(name = "vote_count")
    val voteCount: Int,

    @field:Json(name = "status")
    val status: String,

    @field:Json(name = "genres")
    val genre: List<GenreResponse>


)