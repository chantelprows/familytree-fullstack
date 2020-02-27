package Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import DataAccess.AuthTokenDao;
import DataAccess.UserDao;
import DataTransfer.LoginRequest;
import DataTransfer.RegisterRequest;
import DataTransfer.RegisterResult;
import Model.AuthToken;
import Model.User;

import static org.junit.Assert.*;

public class RegistrationTest {

    private User user;
    private UserDao ud;
    private RegisterResult rr;
    private RegisterResult rr2;
    private RegisterRequest r;
    private Registration registration;

    @Before
    public void setUp() throws Exception {
        user = new User();
        ud = new UserDao();
        rr = new RegisterResult();
        rr2 = new RegisterResult();
        r = new RegisterRequest();
        registration = new Registration();
        r.setUsername("username4");
        r.setEmail("email4");
        r.setPassword("password");
        r.setFirstName("Lilly");
        r.setLastName("Bean");
        r.setGender("f");

        rr2.setUsername("username4");
        rr2.setErrorMessage(null);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createUser() {
        rr = registration.createUser(r);

        Assert.assertEquals(rr.getErrorMessage(), rr2.getErrorMessage());
        Assert.assertEquals(rr.getUsername(), rr2.getUsername());
    }
}