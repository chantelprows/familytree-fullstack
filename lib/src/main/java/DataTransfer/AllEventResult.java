package DataTransfer;

import java.util.Objects;
import java.util.Set;

import Model.Event;

/**
 * All event result class
 */

public class AllEventResult {

    private Set<Event> events;
    private String errorMessage;

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllEventResult that = (AllEventResult) o;
        return Objects.equals(events, that.events) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(events, errorMessage);
    }
}
