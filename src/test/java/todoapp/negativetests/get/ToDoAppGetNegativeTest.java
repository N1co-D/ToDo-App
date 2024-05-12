package todoapp.negativetests.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.given;
import static todoapp.negativetests.BaseTest.*;

@Slf4j
public class ToDoAppGetNegativeTest {

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