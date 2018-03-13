package br.com.invite.domain.core

import org.springframework.data.jpa.domain.AbstractPersistable
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

abstract class BaseEntity<PK : Serializable> : AbstractPersistable<PK>() {

    public override fun setId(id: PK) {
        super.setId(id)
    }

}