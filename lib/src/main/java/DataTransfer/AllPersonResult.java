package DataTransfer;

import java.util.Objects;
import java.util.Set;

import Model.Person;

/**
 * all person result class
 */

public class AllPersonResult {

    private Set<Person> people;
    private String errorMessage;

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
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
        AllPersonResult that = (AllPersonResult) o;
        return Objects.equals(people, that.people) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(people, errorMessage);
    }
}
