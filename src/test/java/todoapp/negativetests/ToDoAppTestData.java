package todoapp.negativetests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;
import todoapp.data.Task;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ToDoAppTestData {
    public static final long ID_FOR_TESTING_UPDATE_PROCESSES = 17033071;
    public static final long ID_FOR_NEGATIVE_TESTING = 100500;
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

        /**
     * TC-ID6
     */
    public static Stream<Arguments> checkAddToDoTaskWithNegativeIdTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(-1)));
    }

    /**
     * TC-ID7
     */
    public static Stream<Arguments> checkAddToDoTaskWithGreaterThatAllowedIdTestData() {
        return Stream.of(Arguments.of(taskDataWithMassiveId(BigInteger.valueOf(2).pow(65))));
    }

    /**
     * TC-ID8
     */
    public static Stream<Arguments> checkAddToDoTaskWithLetterIdValueTestData() {
        return Stream.of(Arguments.of(taskDataWithInvalidId("Hello World")));
    }

    /**
     * TC-ID9
     */
    public static Stream<Arguments> checkAddToDoTaskWithSymbolicValueTestData() {
        return Stream.of(Arguments.of(taskDataWithInvalidId("( @_@ )")));
    }

    /**
     * TC-ID10
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithEmptyBodyTestData() {
        return Stream.of(Arguments.of(ID_FOR_TESTING_UPDATE_PROCESSES));
    }

    /**
     * TC-ID11
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithNegativeIdTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(-1, taskData(-1)));
    }

    /**
     * TC-ID12
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithGreaterThatAllowedIdTestData() {
        return Stream.of(Arguments.of(BigInteger.valueOf(2).pow(65),
                taskDataWithMassiveId(BigInteger.valueOf(2).pow(65))));
    }

    /**
     * TC-ID13
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithLetterIdValueTestData() {
        return Stream.of(Arguments.of("Hello World", taskDataWithInvalidId("Hello World")));
    }

    /**
     * TC-ID14
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithSymbolicIdValueTestData() {
        return Stream.of(Arguments.of("( @_@ )", taskDataWithInvalidId("( @_@ )")));
    }

    /**
     * TC-ID15
     */
    public static Stream<Arguments> checkAddToDoTaskWithNumericCompleteValueTestData() {
        return Stream.of(Arguments.of(taskDataWithNumericComplete(1703)));
    }

    /**
     * TC-ID16
     */
    public static Stream<Arguments> checkAddToDoTaskWithLetterCompleteValueTestData() {
        return Stream.of(Arguments.of(taskDataWithInvalidComplete("Hello World")));
    }

    /**
     * TC-ID17
     */
    public static Stream<Arguments> checkAddToDoTaskWithSymbolicCompleteValueTestData() {
        return Stream.of(Arguments.of(taskDataWithInvalidComplete("( @‿@ )")));
    }

    /**
     * TC-ID18
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithNumericCompleteValueTestData() {
        return Stream.of(Arguments.of(ID_FOR_TESTING_UPDATE_PROCESSES, taskDataWithNumericComplete(1703)));
    }

    /**
     * TC-ID19
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithLetterCompleteValueTestData() {
        return Stream.of(Arguments.of(ID_FOR_TESTING_UPDATE_PROCESSES,
                taskDataWithInvalidComplete("Hello World")));
    }

    /**
     * TC-ID20
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithSymbolicCompleteValueTestData() {
        return Stream.of(Arguments.of(ID_FOR_TESTING_UPDATE_PROCESSES,
                taskDataWithInvalidComplete("( @_@ )")));
    }

        /**
     * TC-ID22
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithNonExistentIdTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(ID_FOR_NEGATIVE_TESTING, updateTaskData(ID_FOR_NEGATIVE_TESTING)));
    }

    /**
     * TC-ID23
     */
    public static Stream<Arguments> checkDeleteToDoTaskWithNonExistentIdTestData() {
        return Stream.of(Arguments.of(ID_FOR_NEGATIVE_TESTING, USERNAME, PASSWORD));
    }

    /**
     * TC-ID24
     */
    public static Stream<Arguments> checkDeleteToDoTaskWithInvalidUsernameTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(24), 24, "none", PASSWORD));
    }

    /**
     * TC-ID25
     */
    public static Stream<Arguments> checkDeleteToDoTaskWithInvalidPasswordTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(25), 25, USERNAME, "none"));
    }

    /**
     * TC-ID26
     */
    public static Stream<Arguments> checkAddToDoTaskWithNumericTextValueTestData() {
        return Stream.of(Arguments.of(taskDataWithNumericText(1703)));
    }

    /**
     * TC-ID27
     */
    public static Stream<Arguments> checkUpdateToDoTaskWithNumericTextValueTestData() {
        return Stream.of(Arguments.of(ID_FOR_TESTING_UPDATE_PROCESSES, taskDataWithNumericText(1703)));
    }

    /**
     * TC-ID28
     */
    public static Stream<Arguments> checkAddSeveralToDoTaskWithSameIdTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(taskData(28),
                secondTaskData(28, "14:00 Something to complete"), 28));
    }

    /**
     * TC-ID31
     */
    public static Stream<Arguments> checkGetTodosWithNegativeOffsetTestData() {
        return Stream.of(Arguments.of(60, 5, -2));
    }

    /**
     * TC-ID32
     */
    public static Stream<Arguments> checkGetTodosWithNegativeLimitTestData() {
        return Stream.of(Arguments.of(70, 5, -3));
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

    private static String taskDataWithInvalidId(String taskId) {
        return "{\"id\": \"" + taskId + "\", \"text\": \"Something to complete\", \"completed\": false}";
    }

    private static String taskDataWithMassiveId(BigInteger taskId) {
        return "{\"id\": \"" + taskId + "\", \"text\": \"Something to complete\", \"completed\": false}";
    }

    private static String taskDataWithNumericText(int text) {
        return "{\"id\": " + ID_FOR_TESTING_UPDATE_PROCESSES + ", \"text\": " + text + ", \"completed\": false}";
    }

    private static String taskDataWithNumericComplete(long completeStatus) {
        return "{\"id\": " + ID_FOR_TESTING_UPDATE_PROCESSES + ", \"text\": \"Something to complete\", \"completed\": "
                + completeStatus + "}";
    }

    private static String taskDataWithInvalidComplete(String completeStatus) {
        return "{\"id\": " + ID_FOR_TESTING_UPDATE_PROCESSES + ", \"text\": \"Something to complete\", \"completed\": "
                + completeStatus + "}";
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