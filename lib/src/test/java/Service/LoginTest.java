package Service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import DataAccess.AuthTokenDao;
import DataAccess.UserDao;
import DataTransfer.LoginRequest;
import DataTransfer.LoginResult;
import Model.AuthToken;
import Model.User;

import static org.junit.Assert.*;

public class LoginTest {

    private Login login;
    private LoginRequest r;
    private LoginResult lr;
    private LoginResult lr2;

    @Before
    public void setUp() throws Exception {

        login = new Login();
        r = new LoginRequest();
        lr = new LoginResult();
        lr2 = new LoginResult();
        User user = new User();
        UserDao ud = new UserDao();
        r.setUsername("username");
        r.setPassword("password");

        lr2.setUserName("username");
        lr2.setPersonID("99");

        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setFirstName("Jill");
        user.setLastName("Jones");
        user.setGender("f");
        user.setPersonID("99");

        ud.addUser(user);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loginUser() {
        lr = login.loginUser(r);

        Assert.assertEquals(lr2.getPersonID(), lr.getPersonID());
        Assert.assertEquals(lr2.getUserName(), lr.getUserName());

    }
}