package it.erika.albanese.itineraryplanner.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TRAVEL_ITINERARY")
@Getter
@Setter
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

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "TRAVEL_ITINERARY_LEG",
            joinColumns = @JoinColumn(name = "itinerary_id"),
            inverseJoinColumns = @JoinColumn(name = "leg_id"))
    Set<Leg> legs;

}
