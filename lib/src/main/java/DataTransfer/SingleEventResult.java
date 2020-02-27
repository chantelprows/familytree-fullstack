package DataTransfer;

import java.util.Objects;

/**
 * single event result class
 */

public class SingleEventResult {

    private String descendant;
    private String eventID;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private String year;
    private String errorMessage;

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
        SingleEventResult that = (SingleEventResult) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Objects.equals(descendant, that.descendant) &&
                Objects.equals(eventID, that.eventID) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(country, that.country) &&
                Objects.equals(city, that.city) &&
                Objects.equals(eventType, that.eventType) &&
                Objects.equals(year, that.year) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(descendant, eventID, personID, latitude, longitude, country, city, eventType, year, errorMessage);
    }
}
