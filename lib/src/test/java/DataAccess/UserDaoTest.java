package DataAccess;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import Model.User;

import static org.junit.Assert.*;

public class UserDaoTest {

    private UserDao ud;
    private User user;

    @Before
    public void setUp() throws Exception {
        ud = new UserDao();
        user = new User();
        user.setUsername("username2");
        user.setPassword("password");
        user.setEmail("email1");
        user.setFirstName("Jill");
        user.setLastName("Jones");
        user.setGender("f");
        user.setPersonID("5");
    }

    @After
    public void tearDown() throws Exception {
        ud.clear();
    }

    @Test
    public void openConnection() {
    }

    @Test
    public void closeConnection() {
    }

    @Test
    public void addUser() throws SQLException {
        ud.addUser(user);
        Assert.assertEquals(ud.getUser("5"), user);
    }

    @Test
    public void getUser() throws SQLException {
        //tested in addUser()
    }

    @Test
    public void getPersonID() throws SQLException{
        ud.addUser(user);
        String personID = ud.getPersonID(user.getUsername());
        Assert.assertEquals(user.getPersonID(), personID);
    }

    @Test
    public void matchPassword() throws SQLException {
        boolean works = false;

        ud.addUser(user);
        works = ud.matchPassword(user.getUsername(), user.getPassword());

        Assert.assertEquals(works, true);

    }

    @Test
    public void deleteUser() throws SQLException {
        ud.deleteUser(user);
        Assert.assertEquals(ud.getUser("3"), null);
    }

    @Test
    public void clear() throws SQLException {
        User user2 = new User();
        user2.setUsername("username1");
        user2.setPassword("password");
        user2.setEmail("email");
        user2.setFirstName("Jill");
        user2.setLastName("Jones");
        user2.setGender("f");
        user2.setPersonID("4");

        ud.addUser(user);
        ud.addUser(user2);
        ud.clear();

        Assert.assertEquals(ud.getUser("3"), null);
        Assert.assertEquals(ud.getUser("4"), null);
    }
}