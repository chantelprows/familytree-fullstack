package DataTransfer;

import Model.Person;

/**
 * All person request class
 */

public class AllPersonRequest {

    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
