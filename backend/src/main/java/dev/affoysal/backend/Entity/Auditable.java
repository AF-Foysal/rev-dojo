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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private String id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();

    @NotNull
    private String createdBy;

    @NotNull
    private String updatedBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void beforePersist() {
        String userId = RequestContext.getUserId();

        if (userId == null) {
            throw new ApiException();
        }

        setCreatedAt(Instant.now());
        setCreatedBy(userId);

        setUpdatedAt(Instant.now());
        setUpdatedBy(userId);
    }

    @PreUpdate
    public void beforeUpdate() {
        String userId = RequestContext.getUserId();

        if (userId == null) {
            throw new ApiException();
        }

        setUpdatedAt(Instant.now());
        setUpdatedBy(userId);
    }
}