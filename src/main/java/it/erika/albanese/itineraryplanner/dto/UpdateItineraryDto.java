package it.erika.albanese.itineraryplanner.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateItineraryDto {

    private String place;

    private LocalDateTime estimatedTime;

    private boolean completed;

    private List<UUID> legsIds;

}
