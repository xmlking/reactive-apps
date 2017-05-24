package com.example

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime

data class Quote(val ticker: String, val price: BigDecimal, val instant: Instant = Instant.now())

data class GuestBookEntryDto(
        val id: String?,
        val name: String,
        val text: String,
        val date: LocalDateTime = LocalDateTime.now()
)