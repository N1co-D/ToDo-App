package todoapp.isolatedtests.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import todoapp.data.Task;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static todoapp.isolatedtests.BaseTest.*;

@Isolated
@Slf4j
public class ToDoAppIsolatedTest {

    @DisplayName("TC-ID30")
    @Description("TC-ID30 Отправка запроса на получение данных о TODO-задачах c параметрами offset и limit")
    @ParameterizedTest
    @MethodSource("todoapp.isolatedtests.ToDoAppTestData#checkGetTodosWithOffsetAndLimitTestData")
    public void checkGetTodosWithOffsetAndLimit(long testId, int count, int offset, int limit,
                                                List<Task> expectedTasks) throws JsonProcessingException {
        log.info("Добавление списка задач для теста");
        addToDoTaskListForTest(count, testId);

        log.info("Отправка запроса на получение данных о TODO-задачах c параметрами offset и limit");
        List<Task> actualTasks = given()
                .spec(REQUEST_SPECIFICATION)
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .when()
                .get()
                .then()
                .statusCode(200)
                .spec(RESPONSE_SPECIFICATION)
                .extract().body().as(new TypeRef<>() {
                });

        log.info("Проверка на соответствие ожидаемых задач с фактическими");
        assertEquals(expectedTasks, actualTasks);

        log.info("Очищение данных добавленного списка задач");
        deleteToDoTaskListForTest(count, testId);
    }
}