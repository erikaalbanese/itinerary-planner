package it.erika.albanese.itineraryplanner.dto;

import it.erika.albanese.itineraryplanner.utils.ItineraryStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateItineraryDto {

    @NotEmpty(message = "place is mandatory.")
    private String place;

    @NotNull(message = "estimatedTime is mandatory.")
    private LocalDateTime estimatedTime;

    @NotNull(message = "status is mandatory.")
    private ItineraryStatus status;

    @NotNull(message = "legsIds are mandatory.")
    private List<UUID> legsIds;

}
