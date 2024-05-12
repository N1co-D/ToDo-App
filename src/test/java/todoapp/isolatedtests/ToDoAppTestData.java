package todoapp.isolatedtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;
import todoapp.data.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ToDoAppTestData {

    /**
     * TC-ID30
     */
    public static Stream<Arguments> checkGetTodosWithOffsetAndLimitTestData() throws JsonProcessingException {
        List<Task> expectedTasks = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        int offset = 2;
        int limit = 3;
//        int testId = 50 + limit - offset;
        int testId = 50 + offset;
        for (int i = 0; i < limit; i++) {
            String taskJson = taskDataForList(testId, "ToDo №" + testId);
            Task task = objectMapper.readValue(taskJson, Task.class);
            expectedTasks.add(task);
            testId++;
        }
        return Stream.of(Arguments.of(50, 5, offset, limit, expectedTasks));
    }

    public static String taskDataForList(long taskId, String text) throws JsonProcessingException {
        Task task = new Task(taskId, text, false);
        return turnObjectIntoJson(task);
    }

    private static String turnObjectIntoJson(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}