package br.com.invite.domain.core

import java.sql.Timestamp
import java.time.Instant
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate


@MappedSuperclass
data class BaseEntity(var createdAt: Timestamp = Timestamp.from(Instant.now())) {

    lateinit var updatedAt: Timestamp

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L


    @PrePersist
    fun beforePersist() {
        this.createdAt = Timestamp.from(Instant.now())
    }

    @PreUpdate
    fun beforeUpdate() {
        this.updatedAt = Timestamp.from(Instant.now())
    }

}