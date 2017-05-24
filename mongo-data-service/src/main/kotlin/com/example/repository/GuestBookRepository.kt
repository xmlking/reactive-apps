package com.example.repository

import com.example.domain.GuestBookEntry
import com.example.util.count
import com.example.util.find
import com.example.util.tail
import com.example.util.findById
import com.example.util.remove
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
import reactor.core.publisher.Mono

@Repository
class  GuestBookRepository(val template: ReactiveMongoTemplate,
                      val objectMapper: ObjectMapper) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun initData() {
        if (count().block() == 0L) {
            // Drop collections, then create them again NOTE: Collection has to be `capped` for tailable cursors.
            template.dropCollection(GuestBookEntry::class.java)
                    .then(template.createCollection(GuestBookEntry::class.java, CollectionOptions.empty().capped(104857600))) // max: 100MBytes
                    .then().block()
            // Initialize Data
            val guestBookResource = ClassPathResource("data/guestbook.json")
            val guestBookEntries: List<GuestBookEntry> = objectMapper.readValue(guestBookResource.inputStream)
            guestBookEntries.forEach { save(it).block() }
            logger.info("GuestBookEntry data initialization complete")
        }
    }

    fun count() = template.count<GuestBookEntry>()

    fun findAll() = template.find<GuestBookEntry>(Query().with(Sort.by("year")))
    fun tailAll() = template.tail<GuestBookEntry>(Query())

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
