package todoapp.positive.post;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;
import static todoapp.positive.BasePositiveTest.*;

@Slf4j
public class ToDoAppPostPositiveTest {

    @DisplayName("TC-ID2")
    @Description("TC-ID2 Отправка запроса на создание новой TODO-задачи")
    @ParameterizedTest
    @MethodSource("todoapp.positive.ToDoAppTestData#checkAddToDoTaskTestData")
    public void checkAddToDoTask(String taskJson, BigInteger id) {
        log.info("Отправка запроса на создание новой TODO-задачи");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(201);

        log.info("Очищение данных добавленной задачи");
        clearData(id);
    }

    @DisplayName("TC-ID29")
    @Description("TC-ID29 Отправка запроса на создание нескольких новых TODO-задач с одинаковым описанием")
    @ParameterizedTest
    @MethodSource("todoapp.positive.ToDoAppTestData#checkAddSeveralToDoTaskWithSameTextTestData")
    public void checkAddSeveralToDoTaskWithSameText(String firstTaskJson, String secondTaskJson,
                                                    BigInteger firstTaskId, BigInteger secondTaskId) {
        log.info("Добавление задачи для теста");
        addTaskForTest(firstTaskJson);

        log.info("Отправка запроса на создание нескольких новых TODO-задач с одинаковым описанием");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(secondTaskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(201);

        log.info("Очищение данных первой добавленной задачи");
        clearData(firstTaskId);
        log.info("Очищение данных второй добавленной задачи");
        clearData(secondTaskId);
    }
}