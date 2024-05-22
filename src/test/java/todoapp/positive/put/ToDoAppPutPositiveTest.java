package todoapp.positive.put;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import todoapp.consts.Constants;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;
import static todoapp.positive.BasePositiveTest.*;

@Slf4j
public class ToDoAppPutPositiveTest {

    @DisplayName("TC-ID3")
    @Description("TC-ID3 Отправка запроса на обновление данных TODO-задачи")
    @ParameterizedTest
    @MethodSource("todoapp.positive.ToDoAppTestData#checkUpdateToDoTaskTestData")
    public void checkUpdateToDoTask(String taskJson, BigInteger id) {
        log.info("Добавление задачи для теста");
        addTaskForTest(taskJson);

        log.info("Отправка запроса на обновление данных TODO-задачи");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(taskJson)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(200);

        log.info("Очищение данных добавленной задачи");
        clearData(id);
    }

    @DisplayName("TC-ID33")
    @Description("TC-ID33 Проверка возникновения ошибки дублирования задачи при отправке запроса на обновление задачи")
    @ParameterizedTest
    @MethodSource("todoapp.positive.ToDoAppTestData#checkFoDuplicateTaskErrorWhenUpdatingTestData")
    public void checkFoDuplicateTaskErrorWhenUpdating(String firstTaskJsonToAdd, String secondTaskJsonToAdd,
                                                      BigInteger firstTaskId, String taskJsonToUpdate,
                                                      BigInteger updatedId) {
        log.info("Добавление первой задачи для теста");
        addTaskForTest(firstTaskJsonToAdd);

        log.info("Добавление второй задачи для теста");
        addTaskForTest(secondTaskJsonToAdd);

        log.info("Отправка запроса на обновление данных первой TODO-задачи с изменением id внутри тела");
        given()
                .spec(REQUEST_SPECIFICATION)
                .pathParam("id", firstTaskId)
                .contentType(ContentType.JSON)
                .body(taskJsonToUpdate)
                .when()
                .put(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(200); //todo Потенциальный баг: при обновлении id задачи на уже существующее не возникает ошибка, дублируется количество задач в общем списке.

        log.info("Очищение данных добавленных задач");
        clearData(updatedId);
    }
}