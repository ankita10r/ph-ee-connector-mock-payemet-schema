package org.mifos.connector.mockPaymentSchema.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mifos.connector.mockPaymentSchema.schema.AuthorizationRequest;
import org.mifos.connector.mockPaymentSchema.schema.AuthorizationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BatchService {

    @Autowired
    private SendCallbackService sendCallbackService;

    @Async("asyncExecutor")
    public void getAuthorization(String batchId, String clientCorrelationId,
                                                  AuthorizationRequest authRequest, String callbackUrl){
        AuthorizationResponse response = new AuthorizationResponse();

        if (authRequest.getAmount().compareTo(BigDecimal.valueOf(200)) >= 0) {
            response.setStatus("N");
            response.setClientCorrelationId(clientCorrelationId);
            response.setReason("Error getting authorization for the request");
        }
        else {
            response.setClientCorrelationId(clientCorrelationId);
            response.setStatus("Y");
        }

        try {
            sendCallbackService.sendCallback(new ObjectMapper().writeValueAsString(response), callbackUrl);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
