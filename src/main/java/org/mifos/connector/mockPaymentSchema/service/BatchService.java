package org.mifos.connector.mockPaymentSchema.service;

import org.mifos.connector.mockPaymentSchema.schema.AuthorizationRequest;
import org.mifos.connector.mockPaymentSchema.schema.AuthorizationResponse;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    public AuthorizationResponse getAuthorization(String batchId, String clientCorrelationId, AuthorizationRequest authRequest){
        AuthorizationResponse response = new AuthorizationResponse();
        response.setClientCorrelationId(clientCorrelationId);
        response.setStatus("Y");
        return response;
    }
}
