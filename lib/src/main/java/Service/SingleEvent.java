package Service;

import java.sql.SQLException;

import DataAccess.AuthTokenDao;
import DataAccess.EventDao;
import DataAccess.UserDao;
import DataTransfer.SingleEventRequest;
import DataTransfer.SingleEventResult;
import Model.Event;

/**
 * returns single event object with specified id
 */

public class SingleEvent {

    /**gets event from database related to event id
     *
     * @param r single event request object
     * @return single event result object
     */
    public SingleEventResult returnEvent(SingleEventRequest r) {
        Event event;
        EventDao ed = new EventDao();
        SingleEventResult ser = new SingleEventResult();
        AuthTokenDao ad = new AuthTokenDao();

        String username;
        String eventID = r.getEventID();

        boolean related = false;

        try {
            username = ad.getCurUser(r.getAuthToken());
            UserDao ud = new UserDao();
            try {
                ud.getUser(username);
                try {
                    event = ed.getEvent(eventID);
                    String descendant = event.getDescendant();
                    if (descendant.equals(username)) {
                        related = true;
                    }
                    ser.setDescendant(descendant);
                    ser.setEventID(eventID);
                    ser.setPersonID(event.getPerson());
                    ser.setLatitude(event.getLatitude());
                    ser.setLongitude(event.getLongitude());
                    ser.setCountry(event.getCountry());
                    ser.setCity(event.getCity());
                    ser.setEventType(event.getEventType());
                    ser.setYear(Integer.toString(event.getYear()));

                }
                catch (SQLException e) {
                    ser.setErrorMessage("Could not get event");
                }
            }
            catch (SQLException e) {
                ser.setErrorMessage("Could not get event");
            }
        }
        catch (SQLException e) {
            ser.setErrorMessage("Invalid Auth Token");
        }
        if (!related) {
            ser.setErrorMessage("Event does not belong to user");
        }
        return ser;
    }
}
