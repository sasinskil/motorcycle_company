package info.sasinski.validationResponse;

import java.util.List;

public class ConstraintViolationsResponse
{
    private String statusCode;
    private String errorContent;
    private List<String> errorMsg;

    public ConstraintViolationsResponse()
    {}

    public ConstraintViolationsResponse(String statusCode, String errorContent, List<String> errorMsg)
    {
        this.statusCode = statusCode;
        this.errorContent = errorContent;
        this.errorMsg = errorMsg;
    }


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public List<String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(List<String> errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ConstraintViolationsResponse{" +
                "statusCode='" + statusCode + '\'' +
                ", errorContent='" + errorContent + '\'' +
                ", errorMsg=" + errorMsg +
                '}';
    }
}
