package Model;

import java.util.Objects;

/**
 * Auth Token table object
 */

public class AuthToken {

    private String username;
    private String AuthToken;

    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(username, authToken.username) &&
                Objects.equals(AuthToken, authToken.AuthToken);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, AuthToken);
    }
}
