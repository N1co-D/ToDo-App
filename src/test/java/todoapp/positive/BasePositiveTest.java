package todoapp.positive;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import todoapp.consts.Constants;
import todoapp.specs.Specification;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;
import static todoapp.positive.ToDoAppTestData.PASSWORD;
import static todoapp.positive.ToDoAppTestData.USERNAME;

@Slf4j
public class BasePositiveTest {
    public static final RequestSpecification REQUEST_SPECIFICATION = Specification.requestSpecification();
    public static final ResponseSpecification RESPONSE_SPECIFICATION = Specification.responseSpecification();

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

    public static void addTaskForTest(String taskJson) {
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(201);
    }

    public static void clearData(BigInteger id) {
        given()
                .spec(REQUEST_SPECIFICATION)
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .pathParam("id", id)
                .when()
                .delete(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(204);
    }
}