package todoapp.positive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;
import todoapp.data.ToDoTask;
import todoapp.utilities.ConfProperties;

import java.math.BigInteger;
import java.util.stream.Stream;

public class ToDoAppTestData {
    public static final BigInteger ID_FOR_NEGATIVE_TESTING = BigInteger.valueOf(500100);
    public static final String USERNAME = ConfProperties.getProperty("username");
    public static final String PASSWORD = ConfProperties.getProperty("password");

    /**
     * TC-ID2
     */
    public static Stream<Arguments> checkAddToDoTaskTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(BigInteger.valueOf(1)), BigInteger.valueOf(1)));
    }

    /**
     * TC-ID3
     */
    public static Stream<Arguments> checkUpdateToDoTaskTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(updateTaskData(BigInteger.valueOf(2)), BigInteger.valueOf(2)));
    }

    /**
     * TC-ID4
     */
    public static Stream<Arguments> checkDeleteToDoTaskTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(BigInteger.valueOf(3)), BigInteger.valueOf(3), USERNAME,
                PASSWORD));
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
        return Stream.of(Arguments.of(taskData(BigInteger.valueOf(29)),
                secondTaskData(BigInteger.valueOf(30), "Something to complete"), BigInteger.valueOf(29),
                BigInteger.valueOf(30)));
    }

    /**
     * TC-ID33
     */
    public static Stream<Arguments> checkFoDuplicateTaskErrorWhenUpdatingTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(BigInteger.valueOf(33)), taskData(BigInteger.valueOf(34)),
                BigInteger.valueOf(33), taskDataToCheckForDuplication(), BigInteger.valueOf(34)));
    }

    /**
     * TC-ID34
     */
    public static Stream<Arguments> checkValidateJsonSchemeTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(BigInteger.valueOf(35)), "src/main/resources/todo.json",
                BigInteger.valueOf(35)));
    }

    public static String taskData(BigInteger taskId) throws JsonProcessingException {
        ToDoTask toDoTask = new ToDoTask(taskId, "Something to complete", false);
        return turnObjectIntoJson(toDoTask);
    }

    public static String taskDataForList(BigInteger taskId, String text) throws JsonProcessingException {
        ToDoTask toDoTask = new ToDoTask(taskId, text, false);
        return turnObjectIntoJson(toDoTask);
    }

    private static String secondTaskData(BigInteger taskId, String text) throws JsonProcessingException {
        ToDoTask toDoTask = new ToDoTask(taskId, text, false);
        return turnObjectIntoJson(toDoTask);
    }

    public static String taskDataToCheckForDuplication() throws JsonProcessingException {
        ToDoTask toDoTask = new ToDoTask(BigInteger.valueOf(34), "Something to complete", false);
        return turnObjectIntoJson(toDoTask);
    }

    private static String updateTaskData(BigInteger taskId) throws JsonProcessingException {
        ToDoTask toDoTask = new ToDoTask(taskId, "Something to complete - OK", true);
        return turnObjectIntoJson(toDoTask);
    }

    private static String turnObjectIntoJson(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}