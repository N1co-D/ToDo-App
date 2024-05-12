package todoapp.negativetests.post;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.given;
import static todoapp.negativetests.BaseTest.*;

@Slf4j
public class ToDoAppPostNegativeTest {

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
}