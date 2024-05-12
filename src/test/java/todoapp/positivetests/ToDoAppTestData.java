package todoapp.positivetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;
import todoapp.data.Task;

import java.util.stream.Stream;

public class ToDoAppTestData {
    public static final long ID_FOR_NEGATIVE_TESTING = 500100;
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    /**
     * TC-ID2
     */
    public static Stream<Arguments> checkAddToDoTaskTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(1), 1));
    }

    /**
     * TC-ID3
     */
    public static Stream<Arguments> checkUpdateToDoTaskTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(updateTaskData(2), 2));
    }

    /**
     * TC-ID4
     */
    public static Stream<Arguments> checkDeleteToDoTaskTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(3), 3, USERNAME, PASSWORD));
    }

    /**
     * TC-ID21
     */
    public static Stream<Arguments> checkGetToDoTaskWithNonExistentIdTestData() {
        return Stream.of(Arguments.of(ID_FOR_NEGATIVE_TESTING));
    }

    /**
     * TC-ID29
     */
    public static Stream<Arguments> checkAddSeveralToDoTaskWithSameTextTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(29),
                secondTaskData(30, "Something to complete"), 29, 30));
    }

    /**
     * TC-ID33
     */
    public static Stream<Arguments> checkFoDuplicateTaskErrorWhenUpdatingTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(33), taskData(34), 33,
                taskDataToCheckForDuplication(), 34));
    }

    /**
     * TC-ID34
     */
    public static Stream<Arguments> checkValidateJsonSchemeTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(35), "src/main/resources/todo.json", 35));
    }

    public static String taskData(long taskId) throws JsonProcessingException {
        Task task = new Task(taskId, "Something to complete", false);
        return turnObjectIntoJson(task);
    }

    public static String taskDataForList(long taskId, String text) throws JsonProcessingException {
        Task task = new Task(taskId, text, false);
        return turnObjectIntoJson(task);
    }

    private static String secondTaskData(long taskId, String text) throws JsonProcessingException {
        Task task = new Task(taskId, text, false);
        return turnObjectIntoJson(task);
    }

    public static String taskDataToCheckForDuplication() throws JsonProcessingException {
        Task task = new Task(34, "Something to complete", false);
        return turnObjectIntoJson(task);
    }

    private static String updateTaskData(long taskId) throws JsonProcessingException {
        Task task = new Task(taskId, "Something to complete - OK", true);
        return turnObjectIntoJson(task);
    }

    private static String turnObjectIntoJson(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}