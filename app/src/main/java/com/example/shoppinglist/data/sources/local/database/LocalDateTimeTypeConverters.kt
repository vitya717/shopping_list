package com.example.shoppinglist.data.sources.local.database

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

class LocalDateTimeTypeConverters {
    @TypeConverter
    fun toDate(value: String): LocalDateTime {
        return value.let { LocalDateTime.parse(it) }
    }
    @TypeConverter
    fun fromDate(date: LocalDateTime): String {
        return date.toString()
    }
}