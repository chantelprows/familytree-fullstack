package DataAccess;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import Model.Event;
import Model.User;

import static org.junit.Assert.*;

public class EventDaoTest {

    private EventDao ed;
    private Event event;

    @Before
    public void setUp() throws Exception {
        ed = new EventDao();
        event = new Event();

        event.setEventID("4");
        event.setDescendant("username");
        event.setPerson("2");
        event.setLatitude(1.0);
        event.setLongitude(1.0);
        event.setCountry("usa");
        event.setCity("provo");
        event.setEventType("birth");
        event.setYear(2018);
    }


    @After
    public void tearDown() throws Exception {
        ed.clear();
    }

    @Test
    public void openConnection() {
    }

    @Test
    public void closeConnection() {
    }

    @Test
    public void addEvent() throws SQLException {
        ed.addEvent(event);
        Assert.assertEquals(ed.getEvent("4"), event);
    }

    @Test
    public void getEvent() throws SQLException {
        //tested in addEvent()
    }

    @Test
    public void getEvents() throws SQLException {
        Event event2 = new Event();

        event2.setEventID("5");
        event2.setDescendant("username");
        event2.setPerson("2");
        event2.setLatitude(1.0);
        event2.setLongitude(1.0);
        event2.setCountry("usa");
        event2.setCity("provo");
        event2.setEventType("birth");
        event2.setYear(2018);

        ed.addEvent(event);
        ed.addEvent(event2);

        User user = new User();
        user.setUsername("username");

        Set<Event> events = new HashSet<>();
        events.add(event);
        events.add(event2);

        Set<Event> events2 = ed.getEvents(user);

        Assert.assertEquals(events, events2);

    }

    @Test
    public void deleteEvents() throws SQLException {
        Event event2 = new Event();

        event2.setEventID("5");
        event2.setDescendant("username");
        event2.setPerson("2");
        event2.setLatitude(1.0);
        event2.setLongitude(1.0);
        event2.setCountry("usa");
        event2.setCity("provo");
        event2.setEventType("birth");
        event2.setYear(2018);

        User user = new User();
        user.setUsername("username");

        ed.addEvent(event2);
        ed.deleteEvents(user);

        Assert.assertEquals(ed.getEvents(user), null);
    }

    @Test
    public void clear() throws SQLException {
        Event event2 = new Event();

        event2.setEventID("5");
        event2.setDescendant("username2");
        event2.setPerson("2");
        event2.setLatitude(1.0);
        event2.setLongitude(1.0);
        event2.setCountry("usa");
        event2.setCity("provo");
        event2.setEventType("birth");
        event2.setYear(2018);

        ed.addEvent(event2);
        try {
            ed.clear();
        }
        catch (SQLException e){

        }

        Assert.assertEquals(ed.getEvent("5"), null);
        Assert.assertEquals(ed.getEvent("4"), null);
    }
}