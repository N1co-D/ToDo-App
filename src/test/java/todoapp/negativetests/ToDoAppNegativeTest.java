package todoapp.negativetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import todoapp.consts.Constants;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;

@Slf4j
public class ToDoAppNegativeTest extends BaseTest {

    @DisplayName("TC-ID5")
    @Description("TC-ID5 Отправка запроса на создание новой TODO-задачи с пустым телом")
    @Test
    public void checkAddToDoTaskWithEmptyBody() {
        log.info("Отправка запроса на создание новой TODO-задачи с пустым телом");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);
    }

    @DisplayName("TC-ID6")
    @Description("TC-ID6 Отправка запроса на создание новой TODO-задачи с отрицательным значением id")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkAddToDoTaskWithNegativeIdTestData")
    public void checkAddToDoTaskWithNegativeId(String taskJson) {
        log.info("Отправка запроса на создание новой TODO-задачи с отрицательным значением id");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);
    }

    @DisplayName("TC-ID7")
    @Description("TC-ID7 Отправка запроса на создание новой TODO-задачи со значением id выше 64-битного целого числа")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkAddToDoTaskWithGreaterThatAllowedIdTestData")
    public void checkAddToDoTaskWithGreaterThatAllowedId(String taskJson) {
        log.info("Отправка запроса на создание новой TODO-задачи со значением id выше 64-битного целого числа");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);
    }

    @DisplayName("TC-ID8")
    @Description("TC-ID8 Отправка запроса на создание новой TODO-задачи с буквенным значением id")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkAddToDoTaskWithLetterIdValueTestData")
    public void checkAddToDoTaskWithLetterIdValue(String taskJson) {
        log.info("Отправка запроса на создание новой TODO-задачи с буквенным значением id");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);
    }

    @DisplayName("TC-ID9")
    @Description("TC-ID9 Отправка запроса на создание новой TODO-задачи с символьным значением id")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkAddToDoTaskWithSymbolicValueTestData")
    public void checkAddToDoTaskWithSymbolicIdValue(String taskJson) {
        log.info("Отправка запроса на создание новой TODO-задачи с символьным значением id");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);
    }

    @DisplayName("TC-ID10")
    @Description("TC-ID10 Отправка запроса на обновление TODO-задачи с пустым телом")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithEmptyBodyTestData")
    public void checkUpdateToDoTaskWithEmptyBody(long id) {
        log.info("Отправка запроса на обновление TODO-задачи с пустым телом");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(401); //todo Уточнить корректность получаемого статус-кода
    }

    @DisplayName("TC-ID11")
    @Description("TC-ID11 Отправка запроса на обновление TODO-задачи с отрицательным значением id")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithNegativeIdTestData")
    public void checkUpdateToDoTaskWithNegativeId(long id, String taskJson) {
        log.info("Отправка запроса на обновление TODO-задачи с отрицательным значением id");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(404);
    }

    @DisplayName("TC-ID12")
    @Description("TC-ID12 Отправка запроса на обновление TODO-задачи со значением id выше 64-битного целого числа")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithGreaterThatAllowedIdTestData")
    public void checkUpdateToDoTaskWithGreaterThatAllowedId(BigInteger id, String taskJson) {
        log.info("Отправка запроса на обновление TODO-задачи со значением id выше 64-битного целого числа");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(404);
    }

    @DisplayName("TC-ID13")
    @Description("TC-ID13 Отправка запроса на обновление TODO-задачи с буквенным значением id")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithLetterIdValueTestData")
    public void checkUpdateToDoTaskWithLetterIdValue(String id, String taskJson) {
        log.info("Отправка запроса на обновление TODO-задачи с буквенным значением id");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(404);
    }

    @DisplayName("TC-ID14")
    @Description("TC-ID14 Отправка запроса на обновление TODO-задачи с символьным значением id")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithSymbolicIdValueTestData")
    public void checkUpdateToDoTaskWithSymbolicIdValue(String id, String taskJson) {
        log.info("Отправка запроса на обновление TODO-задачи с символьным значением id");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(404);
    }

    @DisplayName("TC-ID15")
    @Description("TC-ID15 Отправка запроса на создание новой TODO-задачи с числовым значением completed")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkAddToDoTaskWithNumericCompleteValueTestData")
    public void checkAddToDoTaskWithNumericCompleteValue(String taskJson) {
        log.info("Отправка запроса на создание новой TODO-задачи с числовым значением completed");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);
    }

    @DisplayName("TC-ID16")
    @Description("TC-ID16 Отправка запроса на создание новой TODO-задачи с буквенным значением completed")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkAddToDoTaskWithLetterCompleteValueTestData")
    public void checkAddToDoTaskWithLetterCompleteValue(String taskJson) {
        log.info("Отправка запроса на создание новой TODO-задачи с буквенным значением completed");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);
    }

    @DisplayName("TC-ID17")
    @Description("TC-ID17 Отправка запроса на создание новой TODO-задачи с символьным значением completed")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkAddToDoTaskWithSymbolicCompleteValueTestData")
    public void checkAddToDoTaskWithSymbolicCompleteValue(String taskJson) {
        log.info("Отправка запроса на создание новой TODO-задачи с символьным значением completed");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);
    }

    @DisplayName("TC-ID18")
    @Description("TC-ID18 Отправка запроса на обновление TODO-задачи с числовым значением completed")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithNumericCompleteValueTestData")
    public void checkUpdateToDoTaskWithNumericCompleteValue(long id, String taskJson) {
        log.info("Отправка запроса на обновление TODO-задачи с числовым значением completed");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(401);
    }

    @DisplayName("TC-ID19")
    @Description("TC-ID19 Отправка запроса на обновление TODO-задачи с буквенным значением completed")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithLetterCompleteValueTestData")
    public void checkUpdateToDoTaskWithLetterCompleteValue(long id, String taskJson) {
        log.info("Отправка запроса на обновление TODO-задачи с буквенным значением completed");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(401);
    }

    @DisplayName("TC-ID20")
    @Description("TC-ID20 Отправка запроса на обновление TODO-задачи с символьным значением completed")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithSymbolicCompleteValueTestData")
    public void checkUpdateToDoTaskWithSymbolicCompleteValue(long id, String taskJson) {
        log.info("Отправка запроса на обновление TODO-задачи с символьным значением completed");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(401);
    }

    @DisplayName("TC-ID22")
    @Description("TC-ID22 Отправка запроса на обновление TODO-задачи с несуществующим id")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithNonExistentIdTestData")
    public void checkUpdateToDoTaskWithNonExistentId(long nonExistentId, String taskJson) {
        log.info("Проверка на отсутствие задачи по id");
        checkNoToDoTask(nonExistentId);

        log.info("Отправка запроса на обновление TODO-задачи с несуществующим id");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", nonExistentId)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(404);
    }

    @DisplayName("TC-ID23")
    @Description("TC-ID23 Отправка запроса на удаление TODO-задачи с несуществующим id")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkDeleteToDoTaskWithNonExistentIdTestData")
    public void checkDeleteToDoTaskWithNonExistentId(long nonExistentId, String username, String password) {
        log.info("Проверка на отсутствие задачи по id");
        checkNoToDoTask(nonExistentId);

        log.info("Отправка запроса на удаление TODO-задачи с несуществующим id");
        given()
                .spec(REQUEST_SPECIFICATION)
                .auth().preemptive().basic(username, password)
                .pathParam("id", nonExistentId)
                .when()
                .delete(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(404);
    }

    @DisplayName("TC-ID24")
    @Description("TC-ID24 Отправка запроса на удаление TODO-задачи с неверными учетными данными (логин)")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkDeleteToDoTaskWithInvalidUsernameTestData")
    public void checkDeleteToDoTaskWithInvalidUsername(String taskJson, long id, String incorrectUsername,
                                                       String password) {
        log.info("Добавление задачи для теста");
        addTaskForTest(taskJson);

        log.info("Отправка запроса на удаление TODO-задачи с неверными учетными данными (логин)");
        given()
                .spec(REQUEST_SPECIFICATION)
                .auth().preemptive().basic(incorrectUsername, password)
                .pathParam("id", id)
                .when()
                .delete(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(401);

        log.info("Очищение данных добавленной задачи");
        clearData(id);
    }

    @DisplayName("TC-ID25")
    @Description("TC-ID25 Отправка запроса на удаление TODO-задачи с неверными учетными данными (пароль)")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkDeleteToDoTaskWithInvalidPasswordTestData")
    public void checkDeleteToDoTaskWithInvalidPassword(String taskJson, long id, String username,
                                                       String incorrectPassword) {
        log.info("Добавление задачи для теста");
        addTaskForTest(taskJson);

        log.info("Отправка запроса на удаление TODO-задачи с неверными учетными данными (пароль)");
        given()
                .spec(REQUEST_SPECIFICATION)
                .auth().preemptive().basic(username, incorrectPassword)
                .pathParam("id", id)
                .when()
                .delete(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(401);

        log.info("Очищение данных добавленной задачи");
        clearData(id);
    }

    @DisplayName("TC-ID26")
    @Description("TC-ID26 Отправка запроса на создание новой TODO-задачи с числовым значением text")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkAddToDoTaskWithNumericTextValueTestData")
    public void checkAddToDoTaskWithNumericTextValue(String taskJson) {
        log.info("Отправка запроса на создание новой TODO-задачи с числовым значением text");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);
    }

    @DisplayName("TC-ID27")
    @Description("TC-ID27 Отправка запроса на обновление TODO-задачи с числовым значением text")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkUpdateToDoTaskWithNumericTextValueTestData")
    public void checkUpdateToDoTaskWithNumericTextValue(long id, String taskJson) {
        log.info("Отправка запроса на обновление TODO-задачи с числовым значением text");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(401); //todo Уточнить корректность получаемого статус-кода
    }

    @DisplayName("TC-ID28")
    @Description("TC-ID28 Отправка запроса на создание нескольких новых TODO-задач с одинаковым id")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkAddSeveralToDoTaskWithSameIdTestData")
    public void checkAddSeveralToDoTaskWithSameId(String firstTaskJson, String secondTaskJson, long id) {
        log.info("Добавление задачи для теста");
        addTaskForTest(firstTaskJson);

        log.info("Отправка запроса на создание нескольких новых TODO-задач с одинаковым id");
        given()
                .spec(REQUEST_SPECIFICATION)
                .contentType(ContentType.JSON)
                .body(secondTaskJson)
                .when()
                .post()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);

        log.info("Очищение данных добавленной задачи");
        clearData(id);
    }

    @DisplayName("TC-ID31")
    @Description("TC-ID31 Отправка запроса на получение данных о TODO-задачах с отрицательным значением offset")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkGetTodosWithNegativeOffsetTestData")
    public void checkGetTodosWithNegativeOffset(long testId, int count, int offset) throws JsonProcessingException {
        log.info("Добавление списка задач для теста");
        addToDoTaskListForTest(count, testId);

        log.info("Отправка запроса на получение данных о TODO-задачах с отрицательным значением offset");
        given()
                .spec(REQUEST_SPECIFICATION)
                .queryParam("offset", offset)
                .when()
                .get()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);

        log.info("Очищение данных добавленного списка задач");
        deleteToDoTaskListForTest(count, testId);
    }

    @DisplayName("TC-ID32")
    @Description("TC-ID32 Отправка запроса на получение данных о TODO-задачах с отрицательным значением limit")
    @ParameterizedTest
    @MethodSource("todoapp.negativetests.ToDoAppTestData#checkGetTodosWithNegativeLimitTestData")
    public void checkGetTodosWithNegativeLimit(long testId, int count, int limit) throws JsonProcessingException {
        log.info("Добавление списка задач для теста");
        addToDoTaskListForTest(count, testId);

        log.info("Отправка запроса на получение данных о TODO-задачах с отрицательным значением limit");
        given()
                .spec(REQUEST_SPECIFICATION)
                .queryParam("limit", limit)
                .when()
                .get()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(400);

        log.info("Очищение данных добавленного списка задач");
        deleteToDoTaskListForTest(count, testId);
    }
}