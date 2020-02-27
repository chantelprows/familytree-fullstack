package Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import DataAccess.AuthTokenDao;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;

import static org.junit.Assert.*;

public class ClearerTest {

    private UserDao ud;
    private AuthTokenDao ad;
    private PersonDao pd;
    private EventDao ed;
    private Clearer c;

    @Before
    public void setUp() throws Exception {
        ud = new UserDao();
        ad = new AuthTokenDao();
        pd = new PersonDao();
        ed = new EventDao();
        c = new Clearer();

        Person person = new Person();
        Event event = new Event();
        AuthToken auth = new AuthToken();
        User user = new User();

        event.setEventID("99");
        event.setDescendant("username3");
        event.setPerson("2");
        event.setLatitude(1.0);
        event.setLongitude(1.0);
        event.setCountry("usa");
        event.setCity("provo");
        event.setEventType("birth");
        event.setYear(2018);

        person.setPersonID("30");
        person.setDescendant("username3");
        person.setFirstName("bob");
        person.setLastName("John");
        person.setGender("m");
        person.setFather("1000");
        person.setMother("1001");
        person.setSpouse("1002");

        auth.setAuthToken("99");
        auth.setUsername("username3");

        user.setUsername("username3");
        user.setPassword("password");
        user.setEmail("email3");
        user.setFirstName("Jill");
        user.setLastName("Jones");
        user.setGender("f");
        user.setPersonID("99");

        ud.addUser(user);
        pd.addPerson(person);
        ed.addEvent(event);
        ad.addAuthToken(auth);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void clearData() throws SQLException {
        c.clearData();

        Assert.assertEquals(ad.getAuthToken("99"), null);
        Assert.assertEquals(ud.getUser("99"), null);
        Assert.assertEquals(ed.getEvent("99"), null);
        Assert.assertEquals(pd.getPerson("30"), null);
    }
}