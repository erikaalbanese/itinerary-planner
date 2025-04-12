package it.erika.albanese.itineraryplanner.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TRAVEL_ITINERARY")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String place;

    private LocalDateTime estimatedTime;

    private boolean completed;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @ManyToMany
    @JoinTable(
            name = "TRAVEL_ITINERARY_LEG",
            joinColumns = @JoinColumn(name = "leg_id"),
            inverseJoinColumns = @JoinColumn(name = "itinerary_id"))
    Set<Leg> legs = new HashSet<>();

}
