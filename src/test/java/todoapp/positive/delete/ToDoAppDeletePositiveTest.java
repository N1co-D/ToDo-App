package todoapp.positive.delete;

import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import todoapp.consts.Constants;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;
import static todoapp.positive.BasePositiveTest.*;

@Slf4j
public class ToDoAppDeletePositiveTest {

    @DisplayName("TC-ID4")
    @Description("TC-ID4 Отправка запроса на удаление TODO-задачи")
    @ParameterizedTest
    @MethodSource("todoapp.positive.ToDoAppTestData#checkDeleteToDoTaskTestData")
    public void checkDeleteToDoTask(String taskJson, BigInteger id, String username, String password) {
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
}