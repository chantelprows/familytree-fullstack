package DataAccess;

import java.sql.*;

import Model.Person;
import Model.User;

/**
 * accesses user table in database
 */

public class UserDao {

    public UserDao () {
        openConnection();
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                stmt.executeUpdate("CREATE TABLE if not exists`User` (\n" +
                        "\t`Username`\tTEXT NOT NULL UNIQUE,\n" +
                        "\t`Password`\tTEXT NOT NULL,\n" +
                        "\t`Email`\tTEXT NOT NULL UNIQUE,\n" +
                        "\t`FirstName`\tTEXT NOT NULL,\n" +
                        "\t`LastName`\tTEXT NOT NULL,\n" +
                        "\t`Gender`\tTEXT NOT NULL,\n" +
                        "\t`PersonID`\tTEXT NOT NULL UNIQUE,\n" +
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

    /** Adds user to user table
     *
     */
    public void addUser(User user) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO User (Username, Password, Email, FirstName, LastName, Gender, PersonID)" +
                        " VALUES (?,?,?,?,?,?,?)";
                stmt = connection.prepareStatement(sql);

                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getFirstName());
                stmt.setString(5, user.getLastName());
                stmt.setString(6, user.getGender());
                stmt.setString(7, user.getPersonID());

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
            System.out.println("user could not be added");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
    }

    public boolean matchPassword(String username, String password) {
        openConnection();
        boolean match = false;
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from User where Username = ? and Password = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);

                rs = stmt.executeQuery();

                if (rs.next()) {
                   match = true;
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("password does not match");
            match = false;
            closeConnection(false);
        }
        closeConnection(true);
        return match;
    }

    public User getUser(String personID) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            User finalUser = new User();
            try {
                String sql = "select * from User where (PersonID) = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, personID);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    finalUser.setUsername(rs.getString(1));
                    finalUser.setPassword(rs.getString(2));
                    finalUser.setEmail(rs.getString(3));
                    finalUser.setFirstName(rs.getString(4));
                    finalUser.setLastName(rs.getString(5));
                    finalUser.setGender(rs.getString(6));
                    finalUser.setPersonID(rs.getString(7));
                    closeConnection(true);
                    return finalUser;
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
            System.out.println("could not get user");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
        return null;
    }

    public String getPersonID(String username) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String personID;
            try {
                String sql = "select PersonID from User where username = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    personID = rs.getString(1);
                    closeConnection(true);
                    return personID;
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("could not get person ID");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
        return null;
    }

    /**Deletes single user from user table
     *
     * @param user: user to be deleted
     */
    public void deleteUser(User user) {
        openConnection();
        try {
            PreparedStatement stmt = null;
            String username = user.getUsername();
            try {
                String sql = "delete from User where (PersonID) = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);

                if (stmt.executeUpdate() != 1) {
                    System.out.println("could not delete user");
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

    /** Deletes all users from user table */
    public void clear() throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "delete from User";
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
