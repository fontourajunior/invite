package br.com.invite.domain.core

import org.springframework.util.Assert
import java.io.Serializable
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.validation.constraints.NotNull

@MappedSuperclass
abstract class BaseEntity(@NotNull @Column private var createdAt: Timestamp = Timestamp.from(Instant.now()),
                          @Column private var updatedAt: Timestamp? = null) : Serializable {

    constructor() : this(createdAt = Timestamp.from(Instant.now()))

    @PrePersist
    fun beforePersist() {
        if (this.createdAt == null) {
            this.createdAt = Timestamp.from(Instant.now())
        }
    }

    @PreUpdate
    fun beforeUpdate() {
        this.updatedAt = Timestamp.from(Instant.now())
    }

}