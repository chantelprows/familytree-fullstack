package Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import DataTransfer.LoadRequest;
import DataTransfer.LoadResult;
import Model.Event;
import Model.Person;
import Model.User;

import static org.junit.Assert.*;

public class LoaderTest {

    private Loader loader;
    private UserDao ud;
    private EventDao ed;
    private PersonDao pd;
    private LoadRequest r;
    private LoadResult lr;
    private LoadResult lr2;
    private Event event;
    private Event event2;
    private Event[] events;
    private User user;
    private User user2;
    private User[] users;
    private Person person;
    private Person person2;
    private Person[] people;

    @Before
    public void setUp() throws Exception {
        loader = new Loader();
        ud = new UserDao();
        pd = new PersonDao();
        ed = new EventDao();
        r = new LoadRequest();
        lr = new LoadResult();
        lr2 = new LoadResult();
        event = new Event();
        event2 = new Event();
        events = new Event[2];
        user = new User();
        user2 = new User();
        users = new User[2];
        person = new Person();
        person2 = new Person();
        people = new Person[2];

        event.setEventID("99");
        event.setDescendant("username");
        event.setPerson("2");
        event.setLatitude(1.0);
        event.setLongitude(1.0);
        event.setCountry("usa");
        event.setCity("provo");
        event.setEventType("birth");
        event.setYear(2018);

        person.setPersonID("30");
        person.setDescendant("username");
        person.setFirstName("bob");
        person.setLastName("John");
        person.setGender("m");
        person.setFather("1000");
        person.setMother("1001");
        person.setSpouse("1002");

        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setFirstName("Jill");
        user.setLastName("Jones");
        user.setGender("f");
        user.setPersonID("99");

        event2.setEventID("9");
        event2.setDescendant("username3");
        event2.setPerson("2");
        event2.setLatitude(1.0);
        event2.setLongitude(1.0);
        event2.setCountry("usa");
        event2.setCity("provo");
        event2.setEventType("birth");
        event2.setYear(2018);

        person2.setPersonID("3");
        person2.setDescendant("username3");
        person2.setFirstName("bob");
        person2.setLastName("John");
        person2.setGender("m");
        person2.setFather("1000");
        person2.setMother("1001");
        person2.setSpouse("1002");

        user2.setUsername("username3");
        user2.setPassword("password");
        user2.setEmail("email3");
        user2.setFirstName("Jill");
        user2.setLastName("Jones");
        user2.setGender("f");
        user2.setPersonID("9");

        users[0] = user;
        users[1] = user2;

        people[0] = person;
        people[1] = person2;

        events[0] = event;
        events[1] = event2;

        r.setEvents(events);
        r.setPersons(people);
        r.setUsers(users);

        StringBuilder sb = new StringBuilder();
        sb.append("Successfully added " + users.length + " users, " + people.length + " persons, ");
        sb.append("and " + events.length + " events to the database.");
        lr2.setSuccessMessage(sb.toString());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void load() {
        lr = loader.load(r);

        Assert.assertEquals(lr, lr2);
    }
}