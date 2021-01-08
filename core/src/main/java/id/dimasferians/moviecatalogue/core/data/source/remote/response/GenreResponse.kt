package id.dimasferians.moviecatalogue.core.data.source.remote.response

import com.squareup.moshi.Json

data class GenreResponse(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "name")
    val name: String
)