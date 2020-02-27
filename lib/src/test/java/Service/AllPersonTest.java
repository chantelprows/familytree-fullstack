package Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import DataAccess.AuthTokenDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import DataTransfer.AllPersonRequest;
import DataTransfer.AllPersonResult;
import Model.AuthToken;
import Model.Person;
import Model.User;

import static org.junit.Assert.*;

public class AllPersonTest {

    private AllPersonRequest r;
    private AllPersonResult apr;
    private AllPerson ap;
    private PersonDao pd;
    private Person person;
    private AuthTokenDao ad;
    private AuthToken auth;
    private UserDao ud;
    private User user;

    @Before
    public void setUp() throws Exception {
        r = new AllPersonRequest();
        apr = new AllPersonResult();
        ap = new AllPerson();
        pd = new PersonDao();
        ad = new AuthTokenDao();
        ud = new UserDao();
        person = new Person();
        auth = new AuthToken();
        user = new User();

        r.setAuthToken("99");

        person.setPersonID("30");
        person.setDescendant("username3");
        person.setFirstName("bob");
        person.setLastName("John");
        person.setGender("m");
        person.setFather("1000");
        person.setMother("1001");
        person.setSpouse("1002");

        Set<Person> people = new HashSet<>();
        people.add(person);
        apr.setPeople(people);

        auth.setAuthToken("99");
        auth.setUsername("username3");

        user.setUsername("username3");
        user.setPassword("password");
        user.setEmail("email3");
        user.setFirstName("Jill");
        user.setLastName("Jones");
        user.setGender("f");
        user.setPersonID("99");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllPeople() throws SQLException {
        pd.addPerson(person);
        ud.addUser(user);
        ad.addAuthToken(auth);
        AllPersonResult apr2 = ap.getAllPeople(r);
        Assert.assertEquals(apr2, apr);
    }
}