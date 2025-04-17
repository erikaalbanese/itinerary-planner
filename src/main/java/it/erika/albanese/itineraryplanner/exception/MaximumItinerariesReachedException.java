package it.erika.albanese.itineraryplanner.exception;

public class MaximumItinerariesReachedException extends RuntimeException {
    public MaximumItinerariesReachedException(String message) {
        super(message);
    }
}
