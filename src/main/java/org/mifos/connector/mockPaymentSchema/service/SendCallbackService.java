package org.mifos.connector.mockPaymentSchema.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.stereotype.Service;

@Service
public class SendCallbackService {

    public int sendCallback(String body, String callbackURL){
        Response response = RestAssured.given()
                .baseUri(callbackURL)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post();
        return response.statusCode();
    }
}
