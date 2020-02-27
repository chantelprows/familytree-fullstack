package DataAccess;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import Model.Person;
import Model.User;

import static org.junit.Assert.*;

public class PersonDaoTest {

    private PersonDao pd;
    private Person person;

    @Before
    public void setUp() throws Exception {
        pd = new PersonDao();
        person = new Person();
        person.setPersonID("30");
        person.setDescendant("username");
        person.setFirstName("bob");
        person.setLastName("John");
        person.setGender("m");
        person.setFather("1000");
        person.setMother("1001");
        person.setSpouse(null);
    }

    @After
    public void tearDown() throws Exception {
        pd.clear();
    }

    @Test
    public void openConnection() {
    }

    @Test
    public void closeConnection() {

    }

    @Test
    public void addPerson() throws SQLException {
        pd.addPerson(person);
        Assert.assertEquals(pd.getPerson("30"), person);
    }

    @Test
    public void getPerson() {
        //tested in addPerson()
    }

    @Test
    public void getPeople() throws SQLException {
        Person person2 = new Person();

        person2.setPersonID("300");
        person2.setDescendant("username");
        person2.setFirstName("bob");
        person2.setLastName("John");
        person2.setGender("m");
        person2.setFather("1000");
        person2.setMother("1001");
        person2.setSpouse("1002");

        pd.addPerson(person);
        pd.addPerson(person2);

        User user = new User();
        user.setUsername("username");

        Set<Person> people = new HashSet<>();
        people.add(person);
        people.add(person2);

        Set<Person> people2 = pd.getPeople(user);

        Assert.assertEquals(people, people2);


    }

    @Test
    public void deletePerson() throws SQLException {
        pd.deletePerson(person);
        Assert.assertEquals(pd.getPerson("30"), null);
    }

    @Test
    public void clear() throws SQLException {
        Person person2 = new Person();

        person2.setPersonID("300");
        person2.setDescendant("username");
        person2.setFirstName("bob");
        person2.setLastName("John");
        person2.setGender("m");
        person2.setFather("1000");
        person2.setMother("1001");
        person2.setSpouse("1002");

        pd.addPerson(person);
        pd.addPerson(person2);
        pd.clear();
        Assert.assertEquals(pd.getPerson("30"), null);
        Assert.assertEquals(pd.getPerson("300"), null);

    }
}