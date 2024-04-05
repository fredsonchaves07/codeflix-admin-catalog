package com.fredsonchaves.infraestructure.castmember.persistence;

import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.castmember.CastMemberType;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "CastMember")
@Table(name = "cast_members")
public class CastMemberJpaEntity {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CastMemberType type;

    @Column(nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Deprecated
    public CastMemberJpaEntity() {
    }

    public CastMemberJpaEntity(String id, String name, CastMemberType type, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CastMemberJpaEntity from(final CastMember castMember) {
        return new CastMemberJpaEntity(
                castMember.getId().getValue(),
                castMember.getName(),
                castMember.getType(),
                castMember.getCreatedAt(),
                castMember.getUpdatedAt()
        );
    }

    public CastMember toAggregate() {
        return CastMember.with(
                CastMemberID.from(getId()),
                getName(),
                getType(),
                getCreatedAt(),
                getUpdatedAt()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CastMemberType getType() {
        return type;
    }

    public void setType(CastMemberType type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
