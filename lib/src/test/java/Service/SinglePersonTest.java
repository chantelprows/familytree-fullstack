package Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import DataAccess.AuthTokenDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import DataTransfer.SinglePersonRequest;
import DataTransfer.SinglePersonResult;
import Model.AuthToken;
import Model.Person;
import Model.User;

import static org.junit.Assert.*;

public class SinglePersonTest {

    private SinglePerson sp;
    private SinglePersonRequest r;
    private SinglePersonResult spr;
    private SinglePersonResult spr2;
    private Person person;
    private PersonDao pd;
    private User user;
    private UserDao ud;
    private AuthToken auth;
    private AuthTokenDao ad;

    @Before
    public void setUp() throws Exception {
        sp = new SinglePerson();
        r = new SinglePersonRequest();
        spr = new SinglePersonResult();
        spr2 = new SinglePersonResult();
        person = new Person();
        pd = new PersonDao();
        user = new User();
        ud = new UserDao();
        auth = new AuthToken();
        ad = new AuthTokenDao();

        r.setAuthToken("99");
        r.setPersonID("30");

        person.setPersonID("30");
        person.setDescendant("username");
        person.setFirstName("bob");
        person.setLastName("John");
        person.setGender("m");
        person.setFather("1000");
        person.setMother("1001");
        person.setSpouse("1002");

        auth.setAuthToken("99");
        auth.setUsername("username");

        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setFirstName("Jill");
        user.setLastName("Jones");
        user.setGender("f");
        user.setPersonID("99");

        pd.addPerson(person);
        ad.addAuthToken(auth);
        ud.addUser(user);

        spr2.setDescendant("username");
        spr2.setPersonID("30");
        spr2.setFirstName("bob");
        spr2.setLastName("John");
        spr2.setGender("m");
        spr2.setFather("1000");
        spr2.setMother("1001");
        spr2.setSpouse("1002");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void returnPerson() {
        spr = sp.returnPerson(r);

        Assert.assertEquals(spr, spr2);
    }
}