package todoapp.negative;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import todoapp.consts.Constants;
import todoapp.data.ToDoTask;
import todoapp.specs.Specification;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static todoapp.negative.ToDoAppTestData.*;

@Slf4j
public class BaseNegativeTest {
    public static final RequestSpecification REQUEST_SPECIFICATION = Specification.requestSpecification();
    public static final ResponseSpecification RESPONSE_SPECIFICATION = Specification.responseSpecification();

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

    @BeforeAll
    public static void addResistantTaskForTest() throws JsonProcessingException {
        log.info("Проверка на наличие специальной задачи для тестирования update-процессов");
        if (!isTaskExists(ID_FOR_TESTING_UPDATE_PROCESSES)) {
            log.info("Задача отсутствует. Отправка запроса на её создание");
            given()
                    .spec(REQUEST_SPECIFICATION)
                    .contentType(ContentType.JSON)
                    .body(taskDataResistantToDoForTest())
                    .when()
                    .post()
                    .then()
                    .spec(RESPONSE_SPECIFICATION)
                    .statusCode(201);
        }
        log.info("Задача для тестирования update-процессов имеется в приложении");
    }

    @AfterAll
    public static void deleteResistantTaskForTest() {
        log.info("Проверка на наличие специальной задачи для тестирования update-процессов");
        if (isTaskExists(ID_FOR_TESTING_UPDATE_PROCESSES)) {
            log.info("Задача имеется в приложении. Отправка запроса на её удаление");
            given()
                    .spec(REQUEST_SPECIFICATION)
                    .auth().preemptive().basic(USERNAME, PASSWORD)
                    .pathParam("id", ID_FOR_TESTING_UPDATE_PROCESSES)
                    .when()
                    .delete(Constants.BY_ID)
                    .then()
                    .spec(RESPONSE_SPECIFICATION)
                    .statusCode(204);
        }
        log.info("Задача для тестирования update-процессов отсутствует в приложении");
    }

    public static boolean isTaskExists(BigInteger id) {
        List<ToDoTask> response = given()
                .spec(REQUEST_SPECIFICATION)
                .when()
                .get()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(200)
                .extract().body().as(new TypeRef<>() {
                });

        return response != null && response.stream().anyMatch(task -> task.getId() == id);
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

    public static void addToDoTaskListForTest(int count, BigInteger testId) throws JsonProcessingException {
        for (int i = 0; i < count; i++) {
            addTaskForTest(taskDataForList(testId, "ToDo №" + testId));
            testId = testId.add(BigInteger.ONE);
        }
    }

    public static void deleteToDoTaskListForTest(int count, BigInteger testId) {
        for (int i = 0; i < count; i++) {
            clearData(testId);
            testId = testId.add(BigInteger.ONE);
        }
    }

    public static void checkNoToDoTask(BigInteger expectedId) {
        List<ToDoTask> response = given()
                .spec(REQUEST_SPECIFICATION)
                .when()
                .get()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .extract().body().as(new TypeRef<>() {
                });

        assertFalse(response.stream().anyMatch(task -> task.getId() == expectedId));
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

    private static String taskDataResistantToDoForTest() throws JsonProcessingException {
        ToDoTask toDoTask = new ToDoTask(ID_FOR_TESTING_UPDATE_PROCESSES, "Todo to testing", false);
        return turnObjectIntoJson(toDoTask);
    }

    private static String turnObjectIntoJson(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}