package Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import DataTransfer.FillRequest;
import DataTransfer.FillResult;
import Model.Person;
import Model.User;

import static org.junit.Assert.*;

public class FillerTest {

    private Person person;
    private Filler filler;
    private FillRequest r;
    private FillResult fr;
    private FillResult fr2;
    private User user;
    private UserDao ud;

    @Before
    public void setUp() throws Exception {
        person = new Person();
        filler = new Filler();
        r = new FillRequest();
        fr = new FillResult();
        fr2 = new FillResult();
        user = new User();
        ud = new UserDao();
        person.setPersonID("1");
        person.setDescendant("username");
        person.setFirstName("bob");
        person.setLastName("jones");
        person.setGender("m");

        r.setUsername("username");
        r.setGenerations(1);

        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email3");
        user.setFirstName("Jill");
        user.setLastName("Jones");
        user.setGender("f");
        user.setPersonID("99");

        ud.addUser(user);

        fr2.setSuccessMessage("Successfully added 3 persons and 7 events to the database.");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fill() {
        fr = filler.fill(r);
        Assert.assertEquals(fr, fr2);

    }
}