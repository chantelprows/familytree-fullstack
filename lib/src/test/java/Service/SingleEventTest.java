package Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import DataAccess.AuthTokenDao;
import DataAccess.EventDao;
import DataAccess.UserDao;
import DataTransfer.SingleEventRequest;
import DataTransfer.SingleEventResult;
import Model.AuthToken;
import Model.Event;
import Model.User;

import static org.junit.Assert.*;

public class SingleEventTest {

    private SingleEvent se;
    private SingleEventRequest r;
    private SingleEventResult ser;
    private SingleEventResult ser2;
    private Event event;
    private EventDao ed;
    private AuthToken auth;
    private AuthTokenDao ad;
    private User user;
    private UserDao ud;

    @Before
    public void setUp() throws Exception {
        se = new SingleEvent();
        r = new SingleEventRequest();
        ser = new SingleEventResult();
        ser2 = new SingleEventResult();
        event = new Event();
        ed = new EventDao();
        auth = new AuthToken();
        ad = new AuthTokenDao();
        user = new User();
        ud = new UserDao();

        event.setEventID("99");
        event.setDescendant("username");
        event.setPerson("2");
        event.setLatitude(1.0);
        event.setLongitude(1.0);
        event.setCountry("usa");
        event.setCity("provo");
        event.setEventType("birth");
        event.setYear(2018);

        auth.setAuthToken("99");
        auth.setUsername("username");

        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setFirstName("Jill");
        user.setLastName("Jones");
        user.setGender("f");
        user.setPersonID("99");

        ed.addEvent(event);
        ad.addAuthToken(auth);
        ud.addUser(user);

        r.setEventID("99");
        r.setAuthToken("99");

        ser2.setEventID("99");
        ser2.setDescendant("username");
        ser2.setPersonID("2");
        ser2.setLatitude(1.0);
        ser2.setLongitude(1.0);
        ser2.setCountry("usa");
        ser2.setCity("provo");
        ser2.setEventType("birth");
        ser2.setYear("2018");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void returnEvent() {
        ser = se.returnEvent(r);

        Assert.assertEquals(ser, ser2);
    }
}