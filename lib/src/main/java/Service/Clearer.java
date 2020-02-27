package Service;

import java.sql.SQLException;

import DataAccess.AuthTokenDao;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import DataTransfer.ClearResult;

/**
 * Deletes all data from the database, including user accounts, auth tokens, and generated person
 * and event data
 */

public class Clearer {

    /**clears database
     *
     * @return ClearResult object
     */

    public ClearResult clearData() {
        AuthTokenDao ad = new AuthTokenDao();
        UserDao ud = new UserDao();
        PersonDao pd = new PersonDao();
        EventDao ed = new EventDao();

        ClearResult cr = new ClearResult();

        try {
            ad.clearAll();
            ud.clear();
            pd.clear();
            ed.clear();

            cr.setMessage("Clear succeeded");
        }
        catch (SQLException e) {
            cr.setMessage("Clear failed.");
        }
        return cr;
    }
}
