package com.example.repository

import com.example.domain.GuestBookEntry
import com.example.util.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.CollectionOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Repository
class  GuestBookRepository(val template: ReactiveMongoTemplate,
                      val objectMapper: ObjectMapper) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun initData() {
        if (! template.collectionExists(GuestBookEntry::class.java).block()) {
            // Create collection with capped = true
            template.createCollection(GuestBookEntry::class.java, CollectionOptions(1024 * 1024, 100, true)).block()

            // Initialize Data
            val guestBookResource = ClassPathResource("data/guestbook.json")
            val guestBookEntries: List<GuestBookEntry> = objectMapper.readValue(guestBookResource.inputStream)
            guestBookEntries.forEach { save(it).block() }
            logger.info("GuestBookEntry data initialization complete")
        }
    }

    fun count() = template.count<GuestBookEntry>()

    fun findAll() = template.find<GuestBookEntry>(Query().with(Sort.by("year")))
    fun tailByTimestampGreaterThan(timestamp: Date) = template.tail<GuestBookEntry>(Query(Criteria.where("date").gte(timestamp)))

    fun findOne(id: String) = template.findById<GuestBookEntry>(id)

    fun deleteAll() = template.remove<GuestBookEntry>(Query())
    fun deleteOne(id: String) = template.remove<GuestBookEntry>(Query(Criteria.where("_id").`is`(id)))

    fun save(entry: GuestBookEntry) = template.save(entry)
    fun save(entry: Mono<GuestBookEntry>) = template.save(entry)
}

//@Repository
//interface  GuestBookRepository1 : ReactiveMongoRepository<GuestBookEntry, String> {
//    @Tailable fun findBy(): Flux<GuestBookEntry>
//}
