package com.neito.cinememoire.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val keyword: String,
    val pagesCount: Long,
    val films: List<Film>,
    val searchFilmsCountResult: Long
)

@Serializable
data class Film (
    val filmId: Long,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val type: String,
    val year: String,
    val description: String? = null,
    val filmLength: String? = null,
    val countries: List<CountryS>,
    val genres: List<GenreS>,
    val rating: String,
    val ratingVoteCount: Long,
    val posterUrl: String,
    val posterUrlPreview: String
)

@Serializable
data class CountryS (
    val country: String
)

@Serializable
data class GenreS (
    val genre: String
)
