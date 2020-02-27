package DataAccess;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import Model.Person;
import Model.User;

/**
 * accesses person table in database
 */

public class PersonDao {

    public PersonDao () {
        openConnection();
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                stmt.executeUpdate("CREATE TABLE if not exists `Person` (\n" +
                        "\t`PersonID`\tTEXT NOT NULL UNIQUE,\n" +
                        "\t`Descendant`\tTEXT NOT NULL,\n" +
                        "\t`FirstName`\tTEXT NOT NULL,\n" +
                        "\t`LastName`\tTEXT NOT NULL,\n" +
                        "\t`Gender`\tTEXT NOT NULL,\n" +
                        "\t`Father`\tTEXT,\n" +
                        "\t`Mother`\tTEXT,\n" +
                        "\t`Spouse`\tTEXT,\n" +
                        "\tPRIMARY KEY(`PersonID`)\n" +
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

    public void testConnection() {
        openConnection();
        closeConnection(true);
    }

    /** opens connection with database */
    public void openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyMapDB.db.";
            connection = DriverManager.getConnection(CONNECTION_URL);
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            System.out.println("openConnectionFailed");
        }
    }

    /** closes connection with database */
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
            e.printStackTrace();
        }
    }

    /** adds person object to person table
     *
     * @param person: user associated
     */
    public void addPerson(Person person) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO Person (PersonID, Descendant, FirstName, LastName, Gender," +
                        " Father, Mother, Spouse) VALUES (?,?,?,?,?,?,?,?)";
                stmt = connection.prepareStatement(sql);

                stmt.setString(1, person.getPersonID());
                stmt.setString(2, person.getDescendant());
                stmt.setString(3, person.getFirstName());
                stmt.setString(4, person.getLastName());
                stmt.setString(5, person.getGender());
                stmt.setString(6, person.getFather());
                stmt.setString(7, person.getMother());
                stmt.setString(8, person.getSpouse());

                if (stmt.executeUpdate() != 1) {
                    System.out.println("could not add person");
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("could not add person");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
    }

    /** Gets person associated with specified ID
     *
     * @param personID: person wanted
     * @return person
     */
    public Person getPerson(String personID) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Person finalPerson = new Person();
            try {
                String sql = "SELECT * FROM Person WHERE PersonID = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, personID);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    finalPerson.setPersonID(rs.getString(1));
                    finalPerson.setDescendant(rs.getString(2));
                    finalPerson.setFirstName(rs.getString(3));
                    finalPerson.setLastName(rs.getString(4));
                    finalPerson.setGender(rs.getString(5));
                    finalPerson.setFather(rs.getString(6));
                    finalPerson.setMother(rs.getString(7));
                    finalPerson.setSpouse(rs.getString(8));
                    closeConnection(true);
                    return finalPerson;
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
            System.out.println("could not get person");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
        return null;
    }

    /** Gets all family members of current user
     *
     * @param user: current user
     * @return list of people related to user
     */
    public Set<Person> getPeople(User user) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Set<Person> finalPeople = new HashSet<>();
            String descendant = user.getUsername();
            try {
                String sql = "select * from Person where (Descendant) = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, descendant);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    do {
                        Person curPerson = new Person();
                        curPerson.setPersonID(rs.getString(1));
                        curPerson.setDescendant(rs.getString(2));
                        curPerson.setFirstName(rs.getString(3));
                        curPerson.setLastName(rs.getString(4));
                        curPerson.setGender(rs.getString(5));
                        curPerson.setFather(rs.getString(6));
                        curPerson.setMother(rs.getString(7));
                        curPerson.setSpouse(rs.getString(8));

                        finalPeople.add(curPerson);

                    } while (rs.next());

                    closeConnection(true);
                    return finalPeople;
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
            System.out.println("could not get people");
            closeConnection(false);
            throw new SQLException();
        }

        closeConnection(true);
        return null;
    }

    /**Deletes single person from table
     *
     * @param person: person wanted to delete
     */
    public void deletePerson(Person person) {
        openConnection();
        try {
            PreparedStatement stmt = null;
            String personID = person.getPersonID();
            try {
                String sql = "DELETE FROM Person WHERE PersonID = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, personID);

                if (stmt.executeUpdate() != 1) {
                    System.out.println("could not delete person");
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

    /**
     * Deletes all people from person table
     */
    public void clear() throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "delete from Person";
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
