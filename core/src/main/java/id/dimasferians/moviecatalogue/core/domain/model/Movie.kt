package id.dimasferians.moviecatalogue.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String?,
    val image: String?,
    val voteAverage: Double,
    val voteCount: Int,
)
