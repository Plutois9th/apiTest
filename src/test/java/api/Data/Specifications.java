package api.Data;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.net.URL;

public class Specifications {
    public static RequestSpecification requestSpecification(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();

    }

    public static ResponseSpecification responseSpecificationOK200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static void installSpec(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

    public static ResponseSpecification responseSpecificationError400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static ResponseSpecification responseSpecificationError201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }

        public static ResponseSpecification responseSpecificationUnique ( int status){
            return new ResponseSpecBuilder()
                    .expectStatusCode(status)
                    .build();

        }
        public static ResponseSpecification responseSpecificationError404() {
            return new ResponseSpecBuilder()
                    .expectStatusCode(404)
                    .build();
        }
    }

