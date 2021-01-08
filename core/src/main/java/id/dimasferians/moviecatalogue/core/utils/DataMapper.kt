package id.dimasferians.moviecatalogue.core.utils

import id.dimasferians.moviecatalogue.core.data.source.local.entity.MovieEntity
import id.dimasferians.moviecatalogue.core.data.source.local.entity.TvShowEntity
import id.dimasferians.moviecatalogue.core.data.source.remote.response.MovieDetailResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.MovieResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.TvShowDetailResponse
import id.dimasferians.moviecatalogue.core.data.source.remote.response.TvShowResponse
import id.dimasferians.moviecatalogue.core.domain.model.*

fun List<MovieResponse>.toListMovieDomain(): List<Movie> {
    return this.map {
        Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            image = it.image,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}

fun List<TvShowResponse>.toListTvShowDomain(): List<TvShow> {
    return this.map {
        TvShow(
            id = it.id,
            name = it.name,
            overview = it.overview,
            image = it.image,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}

fun MovieDetailResponse.toMovieDetailDomain(): MovieDetail {
    return MovieDetail(
        id = this.id,
        title = this.title,
        overview = this.overview,
        image = this.image,
        backdropImage = this.backdropImage,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        status = this.status,
        genre = this.genre.map {
            Genre(id = it.id, name = it.name)
        }
    )
}

fun TvShowDetailResponse.toTvShowDetailDomain(): TvShowDetail {
    return TvShowDetail(
        id = this.id,
        name = this.name,
        overview = this.overview,
        image = this.image,
        backdropImage = this.backdropImage,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        status = this.status,
        genre = this.genre.map {
            Genre(id = it.id, name = it.name)
        }
    )
}

fun List<MovieEntity>.toListMovie(): List<Movie> {
    return this.map {
        Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            image = it.image,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}

fun List<TvShowEntity>.toListTvShow(): List<TvShow> {
    return this.map {
        TvShow(
            id = it.id,
            name = it.name,
            overview = it.overview,
            image = it.image,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}

fun MovieEntity.toMovieDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        image = this.image,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        image = this.image,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun TvShowEntity.toTvShowDomain(): TvShow {
    return TvShow(
        id = this.id,
        name = this.name,
        overview = this.overview,
        image = this.image,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun TvShow.toTvShowEntity(): TvShowEntity {
    return TvShowEntity(
        id = this.id,
        name = this.name,
        overview = this.overview,
        image = this.image,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}