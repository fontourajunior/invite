package br.com.invite.domain.core

import org.springframework.data.jpa.domain.AbstractPersistable
import java.io.Serializable
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate


@MappedSuperclass
data class BaseEntity<PK : Serializable>(
        var createdAt: Timestamp = Timestamp.from(Instant.now())) : AbstractPersistable<PK>() {

    lateinit var updatedAt: Timestamp

    public override fun setId(id: PK) {
        super.setId(id)
    }

    @PrePersist
    fun beforePersist() {
        this.createdAt = Timestamp.from(Instant.now())
    }

    @PreUpdate
    fun beforeUpdate() {
        this.updatedAt = Timestamp.from(Instant.now())
    }

}