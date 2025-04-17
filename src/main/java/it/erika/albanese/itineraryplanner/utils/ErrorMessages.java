package it.erika.albanese.itineraryplanner.utils;

import static it.erika.albanese.itineraryplanner.service.ItineraryService.MAX_ITINERARIES;

public class ErrorMessages {
    public static final String LEG_NOT_FOUND = "Leg Not Found";
    public static final String MAXIMUM_ITINERARIES = "You can't create more than " + MAX_ITINERARIES + " itineraries";
    public static final String ITINERARY_NOT_FOUND = "Itinerary Not Found";
    public static final String INVALID_SORT = "Sort must be asc or desc";

    private ErrorMessages() {
    }
}
