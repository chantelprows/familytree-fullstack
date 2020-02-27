package Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.SQLPermission;
import java.util.HashSet;
import java.util.Set;

import DataAccess.AuthTokenDao;
import DataAccess.EventDao;
import DataAccess.UserDao;
import DataTransfer.AllEventRequest;
import DataTransfer.AllEventResult;
import Model.AuthToken;
import Model.Event;
import Model.User;

import static org.junit.Assert.*;

public class AllEventTest {

    private AllEventRequest r;
    private AllEventResult aer;
    private AllEvent ae;
    private EventDao ed;
    private Event event;
    private AuthTokenDao ad;
    private AuthToken auth;
    private UserDao ud;
    private User user;

    @Before
    public void setUp() throws Exception {
        r = new AllEventRequest();
        aer = new AllEventResult();
        ae = new AllEvent();
        ed = new EventDao();
        ad = new AuthTokenDao();
        ud = new UserDao();
        event = new Event();
        user = new User();
        auth = new AuthToken();

        Set<Event> events = new HashSet<>();

        r.setAuthToken("99");

        event.setEventID("99");
        event.setDescendant("username3");
        event.setPerson("2");
        event.setLatitude(1.0);
        event.setLongitude(1.0);
        event.setCountry("usa");
        event.setCity("provo");
        event.setEventType("birth");
        event.setYear(2018);

        events.add(event);
        aer.setEvents(events);

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
    public void returnAllEvents() throws SQLException {
        ed.addEvent(event);
        ad.addAuthToken(auth);
        ud.addUser(user);
        AllEventResult aer1 = ae.returnAllEvents(r);
        Assert.assertEquals(aer1, aer);
    }
}