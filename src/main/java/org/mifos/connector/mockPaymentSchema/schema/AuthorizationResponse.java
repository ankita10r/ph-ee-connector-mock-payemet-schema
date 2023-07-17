package org.mifos.connector.mockPaymentSchema.schema;

public class AuthorizationResponse {

    private String clientCorrelationId;

    private String status;

    private String reason;

    public AuthorizationResponse(){}

    public AuthorizationResponse(String clientCorrelationId, String status){
        this.clientCorrelationId = clientCorrelationId;
        this.status = status;
    }

    public AuthorizationResponse(String clientCorrelationId, String status, String reason){
        this.clientCorrelationId = clientCorrelationId;
        this.status = status;
        this.reason = reason;
    }

    public String getClientCorrelationId() {
        return clientCorrelationId;
    }

    public void setClientCorrelationId(String clientCorrelationId) {
        this.clientCorrelationId = clientCorrelationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
