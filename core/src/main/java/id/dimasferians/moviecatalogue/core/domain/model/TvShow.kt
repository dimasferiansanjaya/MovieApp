package id.dimasferians.moviecatalogue.core.domain.model

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String?,
    val image: String?,
    val voteAverage: Double,
    val voteCount: Int,
)
