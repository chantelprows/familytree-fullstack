package DataTransfer;

import java.util.Objects;

/**
 * fill result class
 */

public class FillResult {

    private String successMessage;
    private String errorMessage;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FillResult that = (FillResult) o;
        return Objects.equals(successMessage, that.successMessage) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(successMessage, errorMessage);
    }
}
