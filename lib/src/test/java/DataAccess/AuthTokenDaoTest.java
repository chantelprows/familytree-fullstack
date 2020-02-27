package DataAccess;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import Model.AuthToken;

import static org.junit.Assert.*;

public class AuthTokenDaoTest {

    private AuthTokenDao au;
    private AuthToken auth;


    @Before
    public void setUp() throws Exception {
        au = new AuthTokenDao();
        auth = new AuthToken();
        auth.setUsername("bob");
        auth.setAuthToken("200");
    }

    @After
    public void tearDown() throws Exception {
        au.clearAll();
    }

    @Test
    public void openConnection() {
    }

    @Test
    public void closeConnection() {
    }

    @Test
    public void getAuthToken() throws SQLException {
        //tested in addToken
    }

    @Test
    public void addAuthToken() throws SQLException {
        au.addAuthToken(auth);
        Assert.assertEquals(au.getAuthToken("200"), auth);

    }

    @Test
    public void getCurUser() throws SQLException {
        au.addAuthToken(auth);
        String username = null;
        username = au.getCurUser(auth.getAuthToken());

        Assert.assertEquals(username, auth.getUsername());

    }

    @Test
    public void delete() throws SQLException {
        au.delete(auth);
        Assert.assertEquals(au.getAuthToken("200"), null);
    }

    @Test
    public void clearAll() throws SQLException {
        AuthToken auth2 = new AuthToken();
        auth2.setUsername("bo");
        auth2.setAuthToken("20");

        au.addAuthToken(auth);
        au.addAuthToken(auth2);
        au.clearAll();

        Assert.assertEquals(au.getAuthToken("200"), null);
        Assert.assertEquals(au.getAuthToken("20"), null);
    }
}