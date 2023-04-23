package com.example.movieappmad23.common

import androidx.room.TypeConverter
import com.example.movieappmad23.models.Genre

class CustomConverters {

    @TypeConverter
    fun stringToList(value: String) = value.split(",").map { it.trim() }

    @TypeConverter
    fun listToString(value: List<String>) = value.joinToString { "," }

    @TypeConverter
    fun genreListToString(value: List<Genre>): String =
        value.joinToString(separator = ",") { it.toString() }

    @TypeConverter
    fun stringToGenreList(value: String): List<Genre> = value.split(",").map { Genre.valueOf(it) }
}