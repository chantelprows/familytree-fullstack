package DataTransfer;

import java.util.Objects;

/**
 * single person result class
 */

public class SinglePersonResult {

    private String descendant;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String father; //optional
    private String mother; //optional
    private String spouse; //optional
    private String errorMessage;

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
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
        SinglePersonResult that = (SinglePersonResult) o;
        return Objects.equals(descendant, that.descendant) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(father, that.father) &&
                Objects.equals(mother, that.mother) &&
                Objects.equals(spouse, that.spouse) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(descendant, personID, firstName, lastName, gender, father, mother, spouse, errorMessage);
    }
}
