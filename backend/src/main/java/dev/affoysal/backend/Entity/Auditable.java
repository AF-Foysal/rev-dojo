package dev.affoysal.backend.Entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dev.affoysal.backend.Domain.RequestContext;
import dev.affoysal.backend.Exception.ApiException;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public abstract class Auditable {

    @Id
    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(name = "id", updatable = false)
    private Long id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();

    @NotNull
    private Long createdBy;

    @NotNull
    private Long updatedBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void beforePersist() {
        // Long userId = RequestContext.getUserId();

        Long userId = 0L;

        // if (userId == null) {
        // throw new ApiException();
        // }

        setCreatedAt(Instant.now());
        setCreatedBy(userId);

        setUpdatedAt(Instant.now());
        setUpdatedBy(userId);
    }

    @PreUpdate
    public void beforeUpdate() {
        // Long userId = RequestContext.getUserId();
        Long userId = 0L;

        // if (userId == null) {
        // throw new ApiException();
        // }

        setUpdatedAt(Instant.now());
        setUpdatedBy(userId);
    }
}