package Service;

import java.sql.SQLException;
import java.util.UUID;

import DataAccess.AuthTokenDao;
import DataAccess.UserDao;
import DataTransfer.LoginRequest;
import DataTransfer.LoginResult;
import Model.AuthToken;
import Model.User;

/**
 * Logs in user and return an auth token
 */

public class Login {

    /** deals with request and result objects
     *
     * @param r Login request object
     * @return Login result object
     */

    public LoginResult loginUser(LoginRequest r) {
        String username = r.getUsername();
        String password = r.getPassword();
        LoginResult lr = new LoginResult();
        AuthToken auth = new AuthToken();
        AuthTokenDao ad = new AuthTokenDao();

        User user = new User();
        UserDao ud = new UserDao();
        user.setUsername(username);
        user.setPassword(password);

        String authToken = UUID.randomUUID().toString();
        if (!ud.matchPassword(username, password)) {
            lr.setErrorMessage("Login Failed");
        }
        else {
            lr.setUserName(username);
            lr.setAuthToken(authToken);
            auth.setUsername(username);
            auth.setAuthToken(authToken);
            try {
                ad.addAuthToken(auth);
                String personID = ud.getPersonID(username);
                lr.setPersonID(personID);
            }
            catch (SQLException e) {
                lr.setErrorMessage("Login Failed");
            }
        }
        return lr;
    }
}
