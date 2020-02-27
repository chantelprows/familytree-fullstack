package DataAccess;

import java.sql.*;

import Model.AuthToken;

/** Accesses and makes changes to Authorization Token table */

public class AuthTokenDao {

    public AuthTokenDao() {
        openConnection();
        try {
            Statement stmt = null;
            try {
                stmt = connection.createStatement();

                stmt.executeUpdate("CREATE TABLE if not exists `AuthToken`(`AuthToken` TEXT NOT NULL UNIQUE," +
                                     " `Username` TEXT NOT NULL, PRIMARY KEY(`AuthToken`))");
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

    /** Returns authorization token from table
     *
     * @param au: active user
     * @return authtoken(s) of user
     *
     * */

    public AuthToken getAuthToken(String au) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            AuthToken finalAuthToken = new AuthToken();
            try {
                String sql = "select * from AuthToken where (AuthToken) = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, au);

                rs = stmt.executeQuery();
                if (rs.next()) {
                    finalAuthToken.setAuthToken(rs.getString("AuthToken"));
                    finalAuthToken.setUsername(rs.getString("Username"));
                    closeConnection(true);
                    return finalAuthToken;
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
            System.out.println("could not get auth token");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
        return null;
    }

    /** Adds a row to the AuthToken table
     *
     * @param authToken: current user
     *
     * */

    public void addAuthToken(AuthToken authToken) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "INSERT INTO AuthToken (AuthToken, Username) VALUES (?,?)";  //wrong sql statement? or no database
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, authToken.getAuthToken());
                stmt.setString(2, authToken.getUsername());

                if (stmt.executeUpdate() != 1) {
                    System.out.println("could not add auth token");
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("could not insert authToken");
            closeConnection(false);
            throw new SQLException();
        }
        closeConnection(true);
    }

    public String getCurUser(String authToken) throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String curUser = new String();
            try {
                String sql = "select Username from AuthToken where AuthToken = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, authToken);

                rs = stmt.executeQuery();
                curUser = rs.getString(1);

                closeConnection(true);

                return curUser;
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
            System.out.println("could not find user");
            closeConnection(false);
            throw new SQLException();
        }
    }

    /** Deletes row from AuthToken table
     *
     * @param au: authToken of row to delete
     *
     * */

    public void delete(AuthToken au) {
        openConnection();
        try {
            PreparedStatement stmt = null;
            String authToken = au.getAuthToken();
            try {
                String sql = "delete from AuthToken where (AuthToken) = (?)";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, authToken);

                if (stmt.executeUpdate() != 1) {
                    System.out.println("could not delete au");
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

    /** Clears all data from AuthToken table */

    public void clearAll() throws SQLException {
        openConnection();
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "delete from AuthToken";
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
