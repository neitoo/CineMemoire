package com.neito.cinememoire.data.remote

object HttpRoutes {
    private const val MAIN_URL = "https://kinopoiskapiunofficial.tech/api"

    const val GET_SEARCH = "${MAIN_URL}/v2.1/films/search-by-keyword"

    const val GET_DETAIL = "${MAIN_URL}/v2.2/films/"
}