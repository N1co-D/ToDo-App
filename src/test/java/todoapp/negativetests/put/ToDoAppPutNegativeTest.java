package todoapp.negativetests.put;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import todoapp.consts.Constants;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;
import static todoapp.negativetests.BaseTest.*;

@Slf4j
public class ToDoAppPutNegativeTest {

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
}