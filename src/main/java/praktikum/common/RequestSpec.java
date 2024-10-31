package praktikum.common;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static praktikum.constants.UrlsRequest.BASE_URL;

public class RequestSpec {

    public RequestSpecification baseRequestSpec() {
        return given(new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setRelaxedHTTPSValidation()
                .build());
    }

    public RequestSpecification baseRequestSpec(String token) {
        return given(new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("authorization", token)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setRelaxedHTTPSValidation()
                .build());
    }
}
