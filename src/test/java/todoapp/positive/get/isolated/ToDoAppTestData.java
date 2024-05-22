package todoapp.positive.get.isolated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;
import todoapp.data.ToDoTask;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ToDoAppTestData {
    public static BigInteger ID_FOR_TESTING_FILTER = BigInteger.valueOf(50);

    /**
     * TC-ID30
     */
    public static Stream<Arguments> checkGetTodosWithOffsetAndLimitTestData() throws JsonProcessingException {
        List<ToDoTask> expectedToDoTasks = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        int offset = 2;
        int limit = 3;
        ID_FOR_TESTING_FILTER = ID_FOR_TESTING_FILTER.add(BigInteger.valueOf(offset));
        for (int i = 0; i < limit; i++) {
            String taskJson = taskDataForList(ID_FOR_TESTING_FILTER, "ToDo №" + ID_FOR_TESTING_FILTER);
            ToDoTask toDoTask = objectMapper.readValue(taskJson, ToDoTask.class);
            expectedToDoTasks.add(toDoTask);
            ID_FOR_TESTING_FILTER = ID_FOR_TESTING_FILTER.add(BigInteger.ONE);
        }
        return Stream.of(Arguments.of(BigInteger.valueOf(50), 5, offset, limit, expectedToDoTasks));
    }

    public static String taskDataForList(BigInteger taskId, String text) throws JsonProcessingException {
        ToDoTask toDoTask = new ToDoTask(taskId, text, false);
        return turnObjectIntoJson(toDoTask);
    }

    private static String turnObjectIntoJson(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}