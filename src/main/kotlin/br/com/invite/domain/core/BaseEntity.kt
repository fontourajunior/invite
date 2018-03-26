package br.com.invite.domain.core

import org.springframework.data.jpa.domain.AbstractPersistable
import java.io.Serializable
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate


@MappedSuperclass
abstract class BaseEntity<PK : Serializable> : AbstractPersistable<PK>() {

    public override fun setId(id: PK) {
        super.setId(id)
    }

    private lateinit var createdAt: Timestamp
    private lateinit var updatedAt: Timestamp


    @PrePersist
    fun beforePersist() {
        this.createdAt = Timestamp.from(Instant.now())
    }

    @PreUpdate
    fun beforeUpdate() {
        this.updatedAt = Timestamp.from(Instant.now())
    }

}