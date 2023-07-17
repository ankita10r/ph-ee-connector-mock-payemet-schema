package org.mifos.connector.mockPaymentSchema.controller;

import org.mifos.connector.mockPaymentSchema.schema.AuthorizationRequest;
import org.mifos.connector.mockPaymentSchema.schema.AuthorizationResponse;
import org.mifos.connector.mockPaymentSchema.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class BatchController {

    @Autowired
    private BatchService batchService;


    @PostMapping("/batches/{batchId}")
    public ResponseEntity<AuthorizationResponse> getAuthorization(@PathVariable String batchId,
                                                                  @RequestHeader("X-Client-Correlation-ID") String clientCorrelationId,
                                                                  @RequestBody AuthorizationRequest authorizationRequest,
                                                                  @RequestParam(value = "command", required = false,
                                                        defaultValue = "authorize") String command){

        AuthorizationResponse authResponse = batchService.getAuthorization(batchId,
                                                clientCorrelationId, authorizationRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
