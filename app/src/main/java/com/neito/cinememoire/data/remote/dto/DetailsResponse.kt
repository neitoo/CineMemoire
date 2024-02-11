package com.neito.cinememoire.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DetailsResponse(
    val kinopoiskId: Long,
    val kinopoiskHDId: Long? = null,
    val imdbId: String,
    val nameRu: String,
    val nameEn: String? = null,
    val nameOriginal: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val coverUrl: String? = null,
    val logoUrl: String? = null,
    val reviewsCount: Long,
    val ratingGoodReview: Long,
    val ratingGoodReviewVoteCount: Long,
    val ratingKinopoisk: Double,
    val ratingKinopoiskVoteCount: Long,
    val ratingImdb: Double,
    val ratingImdbVoteCount: Long,
    val ratingFilmCritics: Long? = null,
    val ratingFilmCriticsVoteCount: Long,
    val ratingAwait: Long? = null,
    val ratingAwaitCount: Long,
    val ratingRfCritics: Long? = null,
    val ratingRfCriticsVoteCount: Long,
    val webUrl: String,
    val year: Long,
    val filmLength: Long,
    val slogan: String? = null,
    val description: String,
    val shortDescription: String? = null,
    val editorAnnotation: String? = null,
    val isTicketsAvailable: Boolean,
    val productionStatus: String? = null,
    val type: String,
    val ratingMpaa: String? = null,
    val ratingAgeLimits: String? = null,
    val countries: List<CountryD>,
    val genres: List<GenreD>,
    val startYear: Long,
    val endYear: Long,
    val serial: Boolean,
    val shortFilm: Boolean,
    val completed: Boolean,
    val hasImax: Boolean,
    val has3D: Boolean,
    val lastSync: String
)

@Serializable
data class CountryD (
    val country: String
)

@Serializable
data class GenreD (
    val genre: String
)
