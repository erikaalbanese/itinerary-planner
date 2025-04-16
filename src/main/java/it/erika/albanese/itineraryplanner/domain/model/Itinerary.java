package it.erika.albanese.itineraryplanner.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.erika.albanese.itineraryplanner.utils.ItineraryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
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

    @Enumerated(EnumType.STRING)
    private ItineraryStatus status;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Itinerary itinerary = (Itinerary) o;
        return Objects.equals(this.id, itinerary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
