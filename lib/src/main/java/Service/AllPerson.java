package Service;

import java.sql.SQLException;
import java.util.Set;

import DataAccess.AuthTokenDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import DataTransfer.AllPersonRequest;
import DataTransfer.AllPersonResult;

import Model.Person;
import Model.User;


/**
 * Returns all family members of the current user (by auth token)
 */

public class AllPerson {

    /**uses auth token to get all people related to user
     *
     * @param r Single person request object
     * @return Single person result object
     */
    public AllPersonResult getAllPeople(AllPersonRequest r) {
        Set<Person> people;
        PersonDao pd = new PersonDao();
        AllPersonResult apr = new AllPersonResult();
        AuthTokenDao ad = new AuthTokenDao();

        UserDao ud = new UserDao();

        String username;
        String personID;

        try {
            username = ad.getCurUser(r.getAuthToken());
            try {
                personID = ud.getPersonID(username);
                User user = ud.getUser(personID);
                people = pd.getPeople(user);

                apr.setPeople(people);
            }
            catch (SQLException e) {
                apr.setErrorMessage("Could not get people");
            }
        }
        catch (SQLException e) {
            apr.setErrorMessage("Invalid Auth Token");
        }
        return apr;
    }
}
