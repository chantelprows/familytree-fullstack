package Service;

import java.sql.SQLException;

import DataAccess.AuthTokenDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import DataTransfer.SinglePersonRequest;
import DataTransfer.SinglePersonResult;
import Model.Person;

/**
 * Returns the single person object with the specified ID
 */

public class SinglePerson {

    /**gets single person from given id
     *
     * @param r Single Person Request object
     * @return Single person return object
     */
    public SinglePersonResult returnPerson(SinglePersonRequest r) {
        Person person;
        PersonDao pd = new PersonDao();
        SinglePersonResult spr = new SinglePersonResult();
        AuthTokenDao ad = new AuthTokenDao();

        String personID = r.getPersonID();
        String username;

        boolean related = false;

        try {
            username = ad.getCurUser(r.getAuthToken());
            UserDao ud = new UserDao();
            try {
                ud.getUser(username);
                try {
                    person = pd.getPerson(personID);
                    if (person.getDescendant().equals(username)) {
                        related = true;
                    }
                    spr.setDescendant(person.getDescendant());
                    spr.setPersonID(personID);
                    spr.setFirstName(person.getFirstName());
                    spr.setLastName(person.getLastName());
                    spr.setGender(person.getGender());
                    spr.setFather(person.getFather());
                    spr.setMother(person.getMother());
                    spr.setSpouse(person.getSpouse());

                    if (!related) {
                        spr.setErrorMessage("Requested person does not belong to user");
                    }
                }
                catch (SQLException e) {
                    spr.setErrorMessage("Cannot retrieve person");
                }
            }
            catch (SQLException e) {
                spr.setErrorMessage("Cannot retrieve person");
            }
        }
        catch (SQLException e) {
            spr.setErrorMessage("Invalid Auth Token");
        }
        return spr;
    }
}
