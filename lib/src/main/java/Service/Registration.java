package Service;

import java.sql.SQLException;
import java.util.UUID;

import DataAccess.AuthTokenDao;
import DataAccess.UserDao;
import DataTransfer.FillRequest;
import DataTransfer.LoginRequest;
import DataTransfer.RegisterRequest;
import DataTransfer.RegisterResult;
import Model.AuthToken;
import Model.User;

/**
 * Creates new user account, 4 generations of ancestor data, logs in user, and return authToken
 */

public class Registration {

    /** deals with request and results
     *
     * @param r Register Request object
     * @return Result object
     */
    public RegisterResult createUser(RegisterRequest r) {
        User user = new User();
        UserDao ud = new UserDao();
        AuthToken auth = new AuthToken();
        AuthTokenDao ad = new AuthTokenDao();
        RegisterResult rr = new RegisterResult();
        LoginRequest lr = new LoginRequest();
        Login login = new Login();
        Filler filler = new Filler();
        FillRequest fr = new FillRequest();

        user.setUsername(r.getUsername());
        user.setPassword(r.getPassword());
        user.setEmail(r.getEmail());
        user.setFirstName(r.getFirstName());
        user.setLastName(r.getLastName());

        try {
            user.setGender(r.getGender());

            String personID = UUID.randomUUID().toString();
            user.setPersonID(personID);

            String authToken = UUID.randomUUID().toString();


            try {
                ud.addUser(user);

                rr.setAuthToken(authToken);
                rr.setPersonID(user.getPersonID());
                rr.setUsername(user.getUsername());

                auth.setAuthToken(authToken);
                auth.setUsername(user.getUsername());
                ad.addAuthToken(auth);

                fr.setGenerations(4);
                fr.setUsername(user.getUsername());
                filler.fill(fr);

                lr.setUsername(user.getUsername());
                lr.setPassword(user.getPassword());
                login.loginUser(lr);

            } catch (SQLException e) {
                rr.setErrorMessage("Registration failed.");
                ud.deleteUser(user);
            }
        }
        catch (SQLException e) {
            rr.setErrorMessage("invalid gender entry");
        }
        return rr;
    }
}
