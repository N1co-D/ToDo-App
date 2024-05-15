package todoapp.positive.get;

import io.qameta.allure.Description;
import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import todoapp.data.ToDoTask;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static todoapp.positive.BasePositiveTest.*;

@Slf4j
public class ToDoAppGetPositiveTest {

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

    @DisplayName("TC-ID21")
    @Description("TC-ID21 Отправка запроса на получение данных о TODO-задачах и проверки отсутствия задачи с несуществующим id")
    @ParameterizedTest
    @MethodSource("todoapp.positive.ToDoAppTestData#checkGetToDoTaskWithNonExistentIdTestData")
    public void checkGetToDoTaskWithNonExistentId(BigInteger expectedId) {
        log.info("Отправка запроса на получение данных о TODO-задачах");
        List<ToDoTask> response = Arrays.asList(given()
                .spec(REQUEST_SPECIFICATION)
                .when()
                .get()
                .then()
                .statusCode(200)
                .spec(RESPONSE_SPECIFICATION)
                .extract().body().as(ToDoTask[].class));

        log.info("Проверка отсутствия задачи с несуществующим id");
        assertFalse(response.stream().anyMatch(task -> task.getId() == expectedId));
    }

    @DisplayName("TC-ID34")
    @Description("TC-ID34 Отправка запроса для проверки json-схемы задачи в теле ответа")
    @ParameterizedTest
    @MethodSource("todoapp.positive.ToDoAppTestData#checkValidateJsonSchemeTestData")
    public void checkValidateJsonScheme(String taskJson, String filePath, BigInteger id) {
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