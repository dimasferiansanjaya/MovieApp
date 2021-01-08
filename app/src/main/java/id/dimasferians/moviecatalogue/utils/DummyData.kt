package id.dimasferians.moviecatalogue.utils

import id.dimasferians.moviecatalogue.core.data.source.remote.response.*

object DummyData {

    fun generateListMovieResponse(): ListMovieResponse {
        return ListMovieResponse(
            page = 1,
            totalPage = 10,
            listMovies = generateListMovie()
        )
    }

    private fun generateListMovie(): List<MovieResponse> {
        val movies = ArrayList<MovieResponse>()

        val movie1 = MovieResponse(
            id = 524047,
            title = "Greenland",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/bNo2mcvSwIvnx8K6y1euAc1TLVq.jpg",
            voteAverage = 6.7,
            voteCount = 100
        )

        val movie2 = MovieResponse(
            id = 622855,
            title = "Jingle Jangle: A Christmas Journey",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/ecpe029lbZf0VOfVp9DK8Voy8Vc.jpg",
            voteAverage = 6.3,
            voteCount = 100,

        )

        val movie3 = MovieResponse(
            id = 340102,
            title = "The New Mutants",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/xrI4EnZWftpo1B7tTvlMUXVOikd.jpg",
            voteAverage = 6.3,
            voteCount = 100,

        )

        val movie4 = MovieResponse(
            id = 667869,
            title = "La vita davanti a sé",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/jDc3O9m8ELHRRu1WXdTC5pCdC4n.jpg",
            voteAverage = 7.2,
            voteCount = 100,

        )

        val movie5 = MovieResponse(
            id = 527400,
            title = "Come Away",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/vwa0DgR2tHMouIDTJWje2k7Pp30.jpg",
            voteAverage = 4.8,
            voteCount = 100,

        )

        val movie6 = MovieResponse(
            id = 400160,
            title = "The SpongeBob Movie: Sponge on the Run",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/jlJ8nDhMhCYJuzOw3f52CP1W8MW.jpg",
            voteAverage = 8.2,
            voteCount = 100,

        )

        val movie7 = MovieResponse(
            id = 337401,
            title = "Mulan",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg",
            voteAverage = 7.2,
            voteCount = 100,

        )

        val movie8 = MovieResponse(
            id = 682377,
            title = "Chick Fight",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/4ZocdxnOO6q2UbdKye2wgofLFhB.jpg",
            voteAverage = 10.0,
            voteCount = 100,

        )

        val movie9 = MovieResponse(
            id = 658412,
            title = "लूडो",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/frB57nMDmu4NnSzjmrq0lEx5iod.jpg",
            voteAverage = 4.0,
            voteCount = 100,

        )

        val movie10 = MovieResponse(
            id = 754800,
            title = "Was wir wollten",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/oKb2rqSaAjp1EQoTw1Z5D3ShCxm.jpg",
            voteAverage = 6.6,
            voteCount = 100,

        )

        movies.add(movie1)
        movies.add(movie2)
        movies.add(movie3)
        movies.add(movie4)
        movies.add(movie5)
        movies.add(movie6)
        movies.add(movie7)
        movies.add(movie8)
        movies.add(movie9)
        movies.add(movie10)

        return movies
    }

    fun generateListTvShowResponse(): ListTvShowResponse {
        return ListTvShowResponse(
            page = 1,
            totalPage = 10,
            listTvShow = generateListTvShow()
        )
    }

    private fun generateListTvShow(): List<TvShowResponse> {
        val tvShows = ArrayList<TvShowResponse>()

        val tv1 = TvShowResponse(
            id = 94305,
            name = "The Walking Dead: World Beyond",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg",
            voteAverage = 7.8,
            voteCount = 100,

        )

        val tv2 = TvShowResponse(
            id = 62286,
            name = "Fear the Walking Dead",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/wGFUewXPeMErCe2xnCmmLEiHOGh.jpg",
            voteAverage = 7.3,
            voteCount = 100,

        )

        val tv3 = TvShowResponse(
            id = 1622,
            name = "Supernatural",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/KoYWXbnYuS3b0GyQPkbuexlVK9.jpg",
            voteAverage = 8.1,
            voteCount = 100,

        )

        val tv4 = TvShowResponse(
            id = 88713,
            name = "We Are Who We Are",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/c3orNlDy9VmOD7xcdn1tqNToAAz.jpg",
            voteAverage = 7.0,
            voteCount = 100,

        )

        val tv5 = TvShowResponse(
            id = 100834,
            name = "Veneno",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/kSENxWb1IS6rUxG070O3aQ2SzJF.jpg",
            voteAverage = 5.9,
            voteCount = 100,

        )

        val tv6 = TvShowResponse(
            id = 82856,
            name = "The Mandalorian",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
            voteAverage = 8.4,
            voteCount = 100,

        )

        val tv7 = TvShowResponse(
            id = 67198,
            name = "Star Trek: Discovery",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/98RYSYsRNKWgrAAFBn0WfploUG7.jpg",
            voteAverage = 7.0,
            voteCount = 100,

        )

        val tv8 = TvShowResponse(
            id = 67136,
            name = "This Is Us",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/bPaVOvW2LK6oYrRv9Ybb196ywub.jpg",
            voteAverage = 8.1,
            voteCount = 100,

        )

        val tv9 = TvShowResponse(
            id = 85964,
            name = "Patria",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/6VxzHeOd237QMhnmVYALeB26bn4.jpg",
            voteAverage = 6.5,
            voteCount = 100,

        )

        val tv10 = TvShowResponse(
            id = 1220,
            name = "The Graham Norton Show",
            overview = "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            image = "/vrbqaBXB8AALynQzpWz6JdCPEJS.jpg",
            voteAverage = 7.1,
            voteCount = 100,

        )

        tvShows.add(tv1)
        tvShows.add(tv2)
        tvShows.add(tv3)
        tvShows.add(tv4)
        tvShows.add(tv5)
        tvShows.add(tv6)
        tvShows.add(tv7)
        tvShows.add(tv8)
        tvShows.add(tv9)
        tvShows.add(tv10)

        return tvShows
    }

    fun generateMovieDetailResponse(id: Int): MovieDetailResponse {
        return MovieDetailResponse(
            id = id,
            title = "Greenland",
            overview = "John Garrity, his estranged wife and their young son embark on a perilous journey to find sanctuary as a planet-killing comet hurtles toward Earth. Amid terrifying accounts of cities getting levelled, the Garrity's experience the best and worst in humanity. As the countdown to the global apocalypse approaches zero, their incredible trek culminates in a desperate and last-minute flight to a possible safe haven.",
            image = "/bNo2mcvSwIvnx8K6y1euAc1TLVq.jpg",
            backdropImage = "/2Fk3AB8E9dYIBc2ywJkxk8BTyhc.jpg",
            voteAverage = 7.1,
            voteCount = 100,
            status = "Released",
            genre = listOf(
                GenreResponse(1,"Drama"),
                GenreResponse(2, "Comedy")
            )
        )
    }

    fun generateTvShowDetailResponse(id: Int): TvShowDetailResponse {
        return TvShowDetailResponse(
            id = id,
            name = "The Walking Dead: World Beyond",
            overview = "A heroic group of teens sheltered from the dangers of the post-apocalyptic world receive a message that inspires them to leave the safety of the only home they have ever known and embark on a cross-country journey to find the one man who can possibly save the world.",
            image = "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg",
            backdropImage = "/pLVrN9B750ehwTFdQ6n3HRUERLd.jpg",
            voteAverage = 7.8,
            voteCount = 100,
            status = "Returning Series",
            genre = listOf(
                GenreResponse(1, "Drama"),
                GenreResponse(2, "Sci-Fi & Fantasy"),
                GenreResponse(3,"Mystery")
            )
        )
    }
}