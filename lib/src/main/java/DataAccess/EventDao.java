package DataAccess;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * accesses event table through database
 */

public class EventDao {

    public EventDao () {
        openConnection();
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                stmt.executeUpdate("CREATE TABLE if not exists `Event` (\n" +
                        "\t`EventID`\tTEXT NOT NULL UNIQUE,\n" +
                        "\t`Descendant`\tTEXT NOT NULL,\n" +
                        "\t`Person`\tTEXT NOT NULL,\n" +
                        "\t`Latitude`\tREAL NOT NULL,\n" +
                        "\t`Longitude`\tREAL NOT NULL,\n" +
                        "\t`Country`\tTEXT NOT NULL,\n" +
                        "\t`City`\tTEXT NOT NULL,\n" +
                        "\t`EventType`\tTEXT NOT NULL,\n" +
                        "\t`Year`\tINTEGER NOT NULL,\n" +
                        "\tPRIMARY KEY(`EventID`)\n" +
                        ")");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            System.out.println("create table failed");
            closeConnection(false);
        }
        closeConnection(true);
    }

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("error");
        }
    }

    private Connection connection;

    /** Opens connection with database */

    public void openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyMapDB.db";
            connection = DriverManager.getConnection(CONNECTION_URL);
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            System.out.println("openConnectionFailed");
        }
    }

    /** Closes connection with database */

    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                connection.commit();
            }
            else {
                connection.rollback();
            }
            connection.close();
            connection = null;
        }
        catch (SQLException e) {
            System.out.println("closeConnection failed");
        }
    }

    /** Adds event to event table in database
     *
     * @param event: event object to add
     */

    public void addEvent(Event event) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "insert into Event (EventID, Descendant, Person, Latitude, Longitude, Country," +
                        " City, EventType, Year) values (?, ?, ?, ?, ?, ?, ?, ? ,?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, event.getEventID());
                stmt.setString(2, event.getDescendant());
                stmt.setString(3, event.getPerson());
                stmt.setDouble(4, event.getLatitude());
                stmt.setDouble(5, event.getLongitude());
                stmt.setString(6, event.getCountry());
                stmt.setString(7, event.getCity());
                stmt.setString(8, event.getEventType());
                stmt.setInt(9, event.getYear());

                if (stmt.executeUpdate() != 1) {
                    System.out.println("could not add event");
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("could not insert event");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
    }

    /** Gets event according to eventID
     *
     * @param eventID: event needed to be accessed
     * @return: event that is accessed
     */

    public Event getEvent(String eventID) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Event finalEvent = new Event();
            try {
                String sql = "select * from Event where (EventID) = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, eventID);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    finalEvent.setEventID(rs.getString(1));
                    finalEvent.setDescendant(rs.getString(2));
                    finalEvent.setPerson(rs.getString(3));
                    finalEvent.setLatitude(rs.getDouble(4));
                    finalEvent.setLongitude(rs.getDouble(5));
                    finalEvent.setCountry(rs.getString(6));
                    finalEvent.setCity(rs.getString(7));
                    finalEvent.setEventType(rs.getString(8));
                    finalEvent.setYear(rs.getInt(9));
                    closeConnection(true);
                    return finalEvent;
                }

            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("could not get event");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
        return null;
    }


    /** Gets list of events associated with a person
     *
     * @param user: person whos events are being accessed
     * @return list of events
     */
    public Set<Event> getEvents(User user) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Set<Event> finalEvents = new HashSet<>();
            String descendant = user.getUsername();
            try {
                String sql = "select * from Event where Descendant = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, descendant);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    do {
                        Event curEvent = new Event();
                        curEvent.setEventID(rs.getString(1));
                        curEvent.setDescendant(rs.getString(2));
                        curEvent.setPerson(rs.getString(3));
                        curEvent.setLatitude(rs.getDouble(4));
                        curEvent.setLongitude(rs.getDouble(5));
                        curEvent.setCountry(rs.getString(6));
                        curEvent.setCity(rs.getString(7));
                        curEvent.setEventType(rs.getString(8));
                        curEvent.setYear(rs.getInt(9));

                        finalEvents.add(curEvent);
                    } while (rs.next());

                    closeConnection(true);
                    return finalEvents;
                }

            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
            catch (SQLException e) {
                System.out.println("could not get events");
                closeConnection(false);
                throw new SQLException();
            }
        closeConnection(true);
        return null;
    }

    public Set<Event> getEvents(String personID) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Set<Event> finalEvents = new HashSet<>();
            try {
                String sql = "select * from Event where person = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, personID);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    do {
                        Event curEvent = new Event();
                        curEvent.setEventID(rs.getString(1));
                        curEvent.setDescendant(rs.getString(2));
                        curEvent.setPerson(rs.getString(3));
                        curEvent.setLatitude(rs.getDouble(4));
                        curEvent.setLongitude(rs.getDouble(5));
                        curEvent.setCountry(rs.getString(6));
                        curEvent.setCity(rs.getString(7));
                        curEvent.setEventType(rs.getString(8));
                        curEvent.setYear(rs.getInt(9));

                        finalEvents.add(curEvent);
                    } while (rs.next());

                    closeConnection(true);
                    return finalEvents;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("could not get events");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
        return null;
    }


    /** deletes list of events associated with descendant
     *
     * @param user: current user
     *
     */

    public void deleteEvents(User user) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            String userName = user.getUsername();
            try {
                String sql = "delete from Event where (Descendant) = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, userName);

                if (stmt.executeUpdate() != 1) {
                    System.out.println("no events to delete");
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("delete failed");
            closeConnection(false);
        }
        closeConnection(true);
    }

    /** clears all events from event table*/
    public void clear() throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "delete from Event";
                stmt = connection.prepareStatement(sql);

                stmt.executeUpdate();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("clear failed");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
    }
}
