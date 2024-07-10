package org.mifos.connector.mockpaymentschema.api;

import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelEnquiryResponseDTO;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelPaymentRequestDTO;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelPaymentResponseDTO;
import org.mifos.connector.mockpaymentschema.service.AirtelMockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirtelMockApi {

    @Autowired
    private AirtelMockService airtelMockService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/standard/v1/payments/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AirtelEnquiryResponseDTO> airtelTransactionEnquiry(@PathVariable String transactionId) {
        return airtelMockService.getTransactionStatus(transactionId);
    }

    @PostMapping("/merchant/v2/payments/")
    public ResponseEntity<AirtelPaymentResponseDTO> getAuthorization(@RequestBody AirtelPaymentRequestDTO airtelPaymentRequestDTO) {
        return airtelMockService.initiateTransaction(airtelPaymentRequestDTO);
    }

}
