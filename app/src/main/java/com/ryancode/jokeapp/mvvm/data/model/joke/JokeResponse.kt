package com.ryancode.jokeapp.mvvm.data.model.joke


data class JokeResponse(
    val error: Boolean,
    val amount: Int,
    val jokes: List<Joke>
)

data class Joke(
    val category: String,
    val type: String,
    val joke: String?,
    val setup: String?,
    val delivery: String?,
    val flags: Flags,
    val id: Int,
    val safe: Boolean,
    val lang: String
)

data class Flags(
    val nsfw: Boolean,
    val religious: Boolean,
    val political: Boolean,
    val racist: Boolean,
    val sexist: Boolean,
    val explicit: Boolean
)
