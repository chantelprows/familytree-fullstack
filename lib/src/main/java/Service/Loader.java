package Service;

import java.sql.SQLException;

import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import DataTransfer.LoadRequest;
import DataTransfer.LoadResult;
import Model.Event;
import Model.Person;
import Model.User;

/**
 * Clears data from database, then loads posted user, person, and event data in to database
 */

public class Loader {

    /** clears then loads given data to database
     *
     * @param r Load Request object
     * @return Load result object
     */
    public LoadResult load(LoadRequest r) {
        Clearer clearer = new Clearer();
        LoadResult lr = new LoadResult();

        UserDao ud = new UserDao();
        PersonDao pd = new PersonDao();
        EventDao ed = new EventDao();

        User[] users = r.getUsers();
        Person[] people = r.getPersons();
        Event[] events = r.getEvents();

        clearer.clearData();

        try {
            for (int i = 0; i < users.length; i++) {
                ud.addUser(users[i]);
            }
            for (int i = 0; i < people.length; i++) {
                pd.addPerson(people[i]);
            }
            for (int i = 0; i < events.length; i++) {
                ed.addEvent(events[i]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Successfully added " + users.length + " users, " + people.length + " persons, ");
            sb.append("and " + events.length + " events to the database.");
            lr.setSuccessMessage(sb.toString());
        }
        catch (SQLException e) {
            lr.setErrorMessage("Could not load data");
        }
        return lr;
    }
}
