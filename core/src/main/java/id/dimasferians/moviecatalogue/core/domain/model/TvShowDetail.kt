package id.dimasferians.moviecatalogue.core.domain.model

data class TvShowDetail(
    val id: Int,
    val name: String,
    val overview: String?,
    val image: String?,
    val backdropImage: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val status: String,
    val genre: List<Genre>
)
