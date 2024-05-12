package todoapp.positivetests;

import io.qameta.allure.Description;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import todoapp.consts.Constants;
import todoapp.data.Task;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
public class ToDoAppPositiveTest extends BaseTest {

    @DisplayName("TC-ID1")
    @Description("TC-ID1 Отправка запроса на получение данных о TODO-задачах")
    @Test
    public void checkGetToDoTasks() {
        log.info("Отправка запроса на получение данных о TODO-задачах");
        given()
                .spec(REQUEST_SPECIFICATION)
                .when()
                .get()
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(200);
    }

    @DisplayName("TC-ID2")
    @Description("TC-ID2 Отправка запроса на создание новой TODO-задачи")
    @ParameterizedTest
    @MethodSource("todoapp.positivetests.ToDoAppTestData#checkAddToDoTaskTestData")
    public void checkAddToDoTask(String taskJson, long id) {
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

    @DisplayName("TC-ID3")
    @Description("TC-ID3 Отправка запроса на обновление данных TODO-задачи")
    @ParameterizedTest
    @MethodSource("todoapp.positivetests.ToDoAppTestData#checkUpdateToDoTaskTestData")
    public void checkUpdateToDoTask(String taskJson, long id) {
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

    @DisplayName("TC-ID4")
    @Description("TC-ID4 Отправка запроса на удаление TODO-задачи")
    @ParameterizedTest
    @MethodSource("todoapp.positivetests.ToDoAppTestData#checkDeleteToDoTaskTestData")
    public void checkDeleteToDoTask(String taskJson, long id, String username, String password) {
        log.info("Добавление задачи для теста");
        addTaskForTest(taskJson);

        log.info("Отправка запроса на удаление TODO-задачи");
        given()
                .spec(REQUEST_SPECIFICATION)
                .auth().preemptive().basic(username, password)
                .pathParam("id", id)
                .when()
                .delete(Constants.BY_ID)
                .then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(204);
    }

    @DisplayName("TC-ID21")
    @Description("TC-ID21 Отправка запроса на получение данных о TODO-задачах и проверки отсутствия задачи с несуществующим id")
    @ParameterizedTest
    @MethodSource("todoapp.positivetests.ToDoAppTestData#checkGetToDoTaskWithNonExistentIdTestData")
    public void checkGetToDoTaskWithNonExistentId(long expectedId) {
        log.info("Отправка запроса на получение данных о TODO-задачах");
        List<Task> response = given()
                .spec(REQUEST_SPECIFICATION)
                .when()
                .get()
                .then()
                .statusCode(200)
                .spec(RESPONSE_SPECIFICATION)
                .extract().body().as(new TypeRef<>() {
                });

        log.info("Проверка отсутствия задачи с несуществующим id");
        assertFalse(response.stream().anyMatch(task -> task.getId() == expectedId));
    }

    @DisplayName("TC-ID29")
    @Description("TC-ID29 Отправка запроса на создание нескольких новых TODO-задач с одинаковым описанием")
    @ParameterizedTest
    @MethodSource("todoapp.positivetests.ToDoAppTestData#checkAddSeveralToDoTaskWithSameTextTestData")
    public void checkAddSeveralToDoTaskWithSameText(String firstTaskJson, String secondTaskJson,
                                                    long firstTaskId, long secondTaskId) {
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

    @DisplayName("TC-ID33")
    @Description("TC-ID33 Проверка возникновения ошибки дублирования задачи при отправке запроса на обновление задачи")
    @ParameterizedTest
    @MethodSource("todoapp.positivetests.ToDoAppTestData#checkFoDuplicateTaskErrorWhenUpdatingTestData")
    public void checkFoDuplicateTaskErrorWhenUpdating(String firstTaskJsonToAdd, String secondTaskJsonToAdd,
                                                      long firstTaskId, String taskJsonToUpdate, long updatedId) {
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

    @DisplayName("TC-ID34")
    @Description("TC-ID34 Отправка запроса для проверка json-схемы задачи в теле ответа")
    @ParameterizedTest
    @MethodSource("todoapp.positivetests.ToDoAppTestData#checkValidateJsonSchemeTestData")
    public void checkValidateJsonScheme(String taskJson, String filePath, long id) {
        log.info("Добавление  задачи для теста");
        addTaskForTest(taskJson);

        log.info("Отправка запроса на получение данных о TODO-задачах");
        Response response = given()
                .spec(REQUEST_SPECIFICATION)
                .when()
                .get();
        response.then()
                .spec(RESPONSE_SPECIFICATION)
                .statusCode(200);

        log.info("Извлечение первой задачи из списка");
        JsonPath jsonPath = response.jsonPath();
        String firstTask = jsonPath.getString("[0]");

        log.info("Проверка на соответствие структуры первой задачи с json-схемой");
        given()
                .body(firstTask)
                .then()
                .body(matchesJsonSchema(new File(filePath)));

        log.info("Очищение данных добавленной задачи");
        clearData(id);
    }
}