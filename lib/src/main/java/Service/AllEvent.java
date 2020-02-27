package Service;

import java.sql.SQLException;
import java.util.Set;

import DataAccess.AuthTokenDao;
import DataAccess.EventDao;
import DataAccess.UserDao;
import DataTransfer.AllEventRequest;
import DataTransfer.AllEventResult;
import Model.Event;
import Model.User;

/**
 * Returns all events for all family members of the current user (using auth token)
 */

public class AllEvent {

    /**gets all events of users family members
     *
     * @param r All event request object
     * @return all event result object
     */
    public AllEventResult returnAllEvents(AllEventRequest r) {
        Set<Event> events;
        EventDao ed = new EventDao();
        AllEventResult aer = new AllEventResult();
        AuthTokenDao ad = new AuthTokenDao();

        UserDao ud = new UserDao();

        String username;
        String personID;

        try {
            username = ad.getCurUser(r.getAuthToken());
            try {
                personID = ud.getPersonID(username);
                User user = ud.getUser(personID);
                events = ed.getEvents(user);

                aer.setEvents(events);
            }
            catch (SQLException e) {
                aer.setErrorMessage("Could not get events");
            }
        }
        catch (SQLException e){
            aer.setErrorMessage("Invalid Auth Token");
        }
        return aer;
    }
}
