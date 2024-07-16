package org.mifos.connector.mockpaymentschema.service;

import java.util.UUID;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelEnquiryResponseDTO;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelEnquiryResponseDataDTO;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelEnquiryResponseDataTransactionDTO;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelPaymentRequestDTO;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelPaymentResponseDTO;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelPaymentResponseDataDTO;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelPaymentResponseDataTransactionDTO;
import org.mifos.connector.common.mobilemoney.airtel.dto.AirtelResponseStatusDTO;
import org.mifos.connector.mockpaymentschema.schema.TransactionStatus;
import org.mifos.connector.mockpaymentschema.schema.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AirtelMockService {

    @Autowired
    private org.mifos.connector.mockpaymentschema.service.SendCallbackService sendCallbackService;

    @Value("${airtel.msisdnSuccessful}")
    private String msisdnSuccessful;

    @Value("${airtel.msisdnFailed}")
    private String msisdnFailed;

    @Value("${airtel.transactionIdSuccessful}")
    private String transactionIdSuccessful;

    @Value("${airtel.transactionIdFailed}")
    private String transactionIdFailed;

    @Value("${airtel.successResponseCode}")
    private String successResponseCode;

    @Value("${airtel.failedResponseCode}")
    private String failedResponseCode;

    @Value("${airtel.pendingResponsCode}")
    private String pendingResponseCode;

    @Value("${airtel.resultCode}")
    private String resultCode;

    public ResponseEntity<AirtelEnquiryResponseDTO> getTransactionStatus(String transactionId) {
        String airtelMoneyId = UUID.randomUUID().toString().replace("-", "");
        String message;
        String status;
        String code;
        String responseCode;
        boolean success;
        HttpStatus httpStatus;

        if (transactionId.equals(transactionIdSuccessful)) {
            message = TransferStatus.SUCCESSFUL.name();
            status = TransactionStatus.TS.name();
            code = HttpStatus.OK.toString();
            responseCode = successResponseCode;
            success = true;
            httpStatus = HttpStatus.OK;
        }

        else if (transactionId.equals(transactionIdFailed)) {
            message = TransferStatus.FAILED.name();
            status = TransactionStatus.TF.name();
            code = HttpStatus.BAD_REQUEST.toString();
            responseCode = failedResponseCode;
            success = false;
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        else {
            message = TransferStatus.IN_PROGRESS.name();
            status = TransactionStatus.TP.name();
            code = HttpStatus.ACCEPTED.toString();
            responseCode = pendingResponseCode;
            success = true;
            httpStatus = HttpStatus.ACCEPTED;
        }

        return airtelEnquiryResponse(airtelMoneyId, transactionId, message, status, code, responseCode, success, httpStatus);
    }

    public ResponseEntity<AirtelPaymentResponseDTO> initiateTransaction(AirtelPaymentRequestDTO airtelPaymentRequestDTO) {
        String message;
        String status;
        String code;
        String responseCode;
        boolean success;
        HttpStatus httpStatus;

        String msisdn = airtelPaymentRequestDTO.getSubscriber().getMsisdn();
        if (msisdn.equals(msisdnSuccessful)) {
            message = TransferStatus.SUCCESSFUL.name();
            status = TransferStatus.SUCCESSFUL.name();
            code = HttpStatus.OK.toString();
            responseCode = successResponseCode;
            success = true;
            httpStatus = HttpStatus.OK;
        }

        else if (msisdn.equals(msisdnFailed)) {
            message = TransferStatus.FAILED.name();
            status = TransferStatus.FAILED.name();
            code = HttpStatus.BAD_REQUEST.toString();
            responseCode = failedResponseCode;
            success = false;
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        else {
            message = TransferStatus.IN_PROGRESS.name();
            status = TransferStatus.IN_PROGRESS.name();
            code = HttpStatus.ACCEPTED.toString();
            responseCode = pendingResponseCode;
            success = true;
            httpStatus = HttpStatus.ACCEPTED;
        }

        return airtelPaymentRespone(message, status, code, responseCode, success, httpStatus);

    }

    private ResponseEntity<AirtelEnquiryResponseDTO> airtelEnquiryResponse(String airtelMoneyId, String transactionId, String message,
            String status, String code, String responseCode, Boolean success, HttpStatus httpStatus) {
        String id = transactionId;

        AirtelEnquiryResponseDataTransactionDTO airtelEnquiryResponseDataTransactionDTO = new AirtelEnquiryResponseDataTransactionDTO(
                airtelMoneyId, id, message, status);
        AirtelEnquiryResponseDataDTO airtelResponseDataDTO = new AirtelEnquiryResponseDataDTO(airtelEnquiryResponseDataTransactionDTO);

        AirtelResponseStatusDTO airtelResponseStatusDTO = new AirtelResponseStatusDTO(code, message, resultCode, responseCode, success);

        AirtelEnquiryResponseDTO responseEntity = new AirtelEnquiryResponseDTO(airtelResponseDataDTO, airtelResponseStatusDTO);
        return ResponseEntity.status(httpStatus).body(responseEntity);

    }

    private ResponseEntity<AirtelPaymentResponseDTO> airtelPaymentRespone(String message, String status, String code, String responseCode,
            Boolean success, HttpStatus httpStatus) {
        boolean id = false;

        AirtelPaymentResponseDataTransactionDTO airtelPaymentResponseDataTransactionDTO = new AirtelPaymentResponseDataTransactionDTO(id,
                status);
        AirtelPaymentResponseDataDTO airtelResponseDataDTO = new AirtelPaymentResponseDataDTO(airtelPaymentResponseDataTransactionDTO);

        AirtelResponseStatusDTO airtelResponseStatusDTO = new AirtelResponseStatusDTO(code, message, resultCode, responseCode, success);

        AirtelPaymentResponseDTO responseEntity = new AirtelPaymentResponseDTO(airtelResponseDataDTO, airtelResponseStatusDTO);
        return ResponseEntity.status(httpStatus).body(responseEntity);
    }

}
