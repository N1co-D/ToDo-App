package todoapp.positivetests.get.isolated;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import todoapp.consts.Constants;
import todoapp.specs.Specification;

import static io.restassured.RestAssured.given;
import static todoapp.positivetests.ToDoAppTestData.*;

@Slf4j
public class BaseTest {
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

    public static void addToDoTaskListForTest(int count, long testId) throws JsonProcessingException {
        for (int i = 0; i < count; i++) {
            addTaskForTest(taskDataForList(testId, "ToDo №" + testId));
            testId++;
        }
    }

    public static void deleteToDoTaskListForTest(int count, long testId) {
        for (int i = 0; i < count; i++) {
            clearData(testId);
            testId++;
        }
    }

    public static void clearData(long id) {
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