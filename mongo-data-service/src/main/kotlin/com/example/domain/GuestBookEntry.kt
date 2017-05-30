package com.example.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import java.time.LocalDateTime;

@Document
data class GuestBookEntry(
        @Id val id: String?,
        val name: String,
        val comment: String,
        val date: LocalDateTime = LocalDateTime.now()
)