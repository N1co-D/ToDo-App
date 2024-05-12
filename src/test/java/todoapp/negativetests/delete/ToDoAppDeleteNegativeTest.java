package todoapp.negativetests.delete;

import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import todoapp.consts.Constants;

import static io.restassured.RestAssured.given;
import static todoapp.negativetests.BaseTest.*;

@Slf4j
public class ToDoAppDeleteNegativeTest {

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
}