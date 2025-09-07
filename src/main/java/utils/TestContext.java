package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    public Response response;
    public Map<String, Object> session = new HashMap<String, Object>();

    public RequestSpecification requestSetup() {
        RestAssured.reset();
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        return RestAssured.given()
//                .log().all()
                .contentType("application/json")
                .accept("application/json");
    }

}
