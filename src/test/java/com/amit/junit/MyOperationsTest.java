package com.amit.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayName("My Operations test for : ")
//@TestInstance(TestInstance.Lifecycle.PERCLASS)
public class MyOperationsTest {

    private MyOperations myOperations;

    private MyOperations globalMyOperations = new MyOperations();

    private static MyOperations myOperationsTest;

    private static List<String> input;

    private static List<String> expectedOutput;

    @BeforeAll
    static public void initMyOperationClass() {

        System.out.println("Initializing test class and other objects for all operations");
        myOperationsTest = new MyOperations();

        input = new ArrayList<>();
        input.add("Amit");
        input.add("Medhansh");

        expectedOutput = new ArrayList<>();
        expectedOutput.add("Hello Amit");
        expectedOutput.add("Hello Medhansh");
    }

    @AfterAll
    static public void flushMyOperationClass() {
        System.out.println("Flushing test class");
    }

    @BeforeEach
    public void initMyOperation() {
        System.out.println("Before each operation, initialize variables");
        myOperations = new MyOperations();
        myOperations.setA(3);
        myOperations.setB(5);
    }


    @AfterEach
    public void flushMyOperation() {
        System.out.println("After each operations, reset variables");
        myOperations.setA(0);
        myOperations.setA(0);
        myOperations =  null;
    }

    @Test
    @DisplayName("Disabled Test")
    @Disabled("Not to be executed ever!!!")
    public void givenDisabledTest() {
        assertFalse(true);
    }

    @Test
    @DisplayName("'Not same' Test")
    public void givenObjectWhenComparedThenNotSame(){
        assertNotSame(new Object(), new Object(), () -> "Object should  have different memory location, so should have different instances");
    }

    @Test
    @DisplayName("'Same' Test")
    public void givenStringWhenComparedThenSame(){
        assertSame("ANC", "ANC", () -> "Same string means same object in JVM, so should be equal");
    }

    @Test
    @Tag("Prod")
    @DisplayName("Addition Operation Pass")
    public void givenNumberWhenAddThenSum1() {
        System.out.println("Addition Operation 1 test case : Test class HASHCODE : " + this.hashCode() + " : BeforeAll Object : " + myOperationsTest.hashCode() + " :  Before Each Object : " + myOperations.hashCode() + " : Instance  variable : " + globalMyOperations.hashCode());
        assertEquals(8, myOperations.add(), () -> "Addition is not working for " + myOperations.getA() + " and " + myOperations.getB());
    }

    @Test
    @Tag("Dev")
    @DisplayName("Addition Operation Fail")
    public void givenNumberWhenAddThenSum2() {
        System.out.println("Addition Operation 2 test case : Test class HASHCODE : " + this.hashCode() + " : BeforeAll Object : " + myOperationsTest.hashCode() + " :  Before Each Object : " + myOperations.hashCode() + " : Instance  variable : " + globalMyOperations.hashCode());
        assertEquals(3, myOperations.add(), () -> "Addition is not working for " + myOperations.getA() + " and " + myOperations.getB());
    }

    @Test
    @Tag("Prod")
    @DisplayName("Multiplication test (assertAll use case)")
    public void givenNumberWhenMultiplyThenMultipliedResult() {
        System.out.println("Multiplication Operation Test case:");
        assertAll(
                () -> assertEquals(2, myOperations.multiply(2, 1), () -> "Multiplication is not correct for 2 and 1"),
                () -> assertEquals(10, myOperations.multiply(10, 1), () -> "Multiplication is not correct for 10 and 1")
        );
    }

    @Test
    @Tag("Prod")
    @DisplayName("Relational test (assertAll use case)")
    public void givenNumberWhenComparedThenBoolean() {
        System.out.println("Relational operation");
        assertAll(
                () -> assertTrue(myOperations.gt(10, 2), "Gt function not correct for 10 and 2"),
                () -> assertFalse(myOperations.lt(10, 2), "lt function not correct for 10 and 2")
        );
    }

    @Test
    @Tag("Prod")
    @DisplayName("Division Operation Test (assertThrows use case)")
    public void givenNumberWhenDividedThenResultOrException() {
        System.out.println("Division Operation Test case with exception");
        assertAll(
                () -> assertEquals(2, myOperations.divide(2, 1), () -> "Division is not correct for 2 and 1"),
                () -> assertEquals(10, myOperations.divide(10, 1), () -> "Division is not correct for 10 and 1"),
                () -> {
                    {
                        Throwable th = assertThrows(IllegalArgumentException.class, () -> myOperations.divide(10, 0));
                        assertEquals("Invalid divisor : 0", th.getMessage(), () -> "Exception message " + th.getMessage() + " did not match with 'Invalid divisor 0'"
                        );
                    }
                },
                () -> assertThrows(IllegalArgumentException.class, () -> myOperations.divide(10, 0), () -> "Handling of divide by 0 is missing"),
                () -> assertDoesNotThrow(() -> myOperations.divide(10, 2), () -> "Handling of divide by 0 is missing")
        );
    }

    @Test
    @Tag("Prod")
    @DisplayName("Array Operation Test (assert on Array use case)")
    public void givenValidArrayWhenConcatenatedThenGiveResult() {
        System.out.println("Array Operation Test case");
        String[] input = {"Amit", "Medhansh"};
        String str = "Hello ";
        String[] expectedOutput = {"Hello Amit", "Hello Medhansh"};
        assertAll(
                () -> assertArrayEquals(input, myOperations.concatenate(input, null), "NULL handling missing"),
                () -> assertArrayEquals(input, myOperations.concatenate(input, ""), "Empty handling missing"),
                () -> assertArrayEquals(null, myOperations.concatenate((String[]) null, ""), "NULL Array handling missing"),
                () -> assertArrayEquals(expectedOutput, myOperations.concatenate(input, str), "Concatenation Logic Not correct")
        );
    }

    @Test
    @Tag("Prod")
    @DisplayName("Iterable Operation Test (assert on List use case)")
    public void givenValidListWhenConcatenatedThenGiveResult() {
        System.out.println(":List Operation Test case");
        String str = "Hello ";
        assertAll(
                () -> assertIterableEquals(input, myOperations.concatenate(input, null), "NULL handling missing"),
                () -> assertIterableEquals(input, myOperations.concatenate(input, ""), "Empty handling missing"),
                () -> assertIterableEquals(expectedOutput, myOperations.concatenate(input, str), "Concatenation Logic Not correct")
        );
    }

    @Test
    @DisplayName("Time out test")
    public void givenOperationWhenPerformedThenPassBeforeTimeOut(){
        System.out.println("Timeout Test case");
        Boolean isSleep = assertTimeout(Duration.ofSeconds(4), () -> { return myOperations.sleep();} , "Not performing in 4 seconds");
        assertEquals(true, isSleep, "Thread was not sleep");

    }

    @Test
    @DisplayName("Time out Preemptively test")
    public void givenOperationWhenPerformedThenPassBeforeTimeOutPreemptively(){
        System.out.println("Timeout Test case");
        Boolean isSleep = assertTimeoutPreemptively(Duration.ofSeconds(4), () -> { return myOperations.sleep();} , "Not performing in 1 seconds");
        assertEquals(true, isSleep, "Thread was not sleep");

    }

    @RepeatedTest(value = 3, name = "{displayName} - {currentRepetition}/{totalRepetitions}")
    @DisplayName("Repeated Operation Test")
    public void givenNumberWhenMultipliedBy0Then0(RepetitionInfo info) {
        System.out.println("Repeated Operation Test case : " + info.getCurrentRepetition());
        int random = new Random().nextInt(15);
        assertEquals(0, myOperations.multiply(0, random), "Multiplication with 0 not giving 0");
    }

    @Nested
    @DisplayName("Subtraction Test")
    class SubtractionTest {

        @Test
        @DisplayName("Equal number - result 0")
        public void givenEqualNumberWhenSubtractedThen0(){
            assertEquals(0, myOperations.subtract(19, 19));
        }

        @Test
        @DisplayName("Negative number ")
        public void givenNegativeNumberWhenSubtractedThenAdded(){
            assertEquals(-38, myOperations.subtract(-19, 19));

        }
    }

    @Test
    @DisplayName("Met Assumption -> testGtassumptionNotMet")
    public void givenAssumptionNotMet(){
        assumeTrue(4 > 5, "Assumption of 4>5 is not valid so skipping test");
        System.out.println("Assumption is not valid");
        assertTrue(4 < 5, "Test 4<5 is not working");
    }

    @Test
    @DisplayName("Did not meet assumption -> testGtassumptionMet")
    public void givenAssumptionMet(){
        assumeFalse(4 > 5, "Assumption of 4>5 is not valid so skipping test");
        System.out.println("Assumption is valid");
        assertTrue(4 < 5, "Test 4<5 is not working");
        assumingThat( 4 < 5, () -> {
            assertTrue(2 < 4, "Test 2<4 is not working");
        });
    }

    @TestFactory
    public Stream<DynamicTest> givenInputListWhenGreetedThenGreeted() {

        return input.stream()
                .map(word ->
                        DynamicTest.dynamicTest("Test translate " + word, () -> {
                            int id = input.indexOf(word);
                            assertEquals(expectedOutput.get(id), myOperations.greet(word));
                        })
                );
    }

    public static List<Integer> data(){
        return Stream.of(1,2,3,4).collect(Collectors.toList());
    }

    @ParameterizedTest
    @MethodSource("data")
    @DisplayName("Parameter Operation Test from Method")
    public void givenNumberMultipliedBy1ThenNumber(Integer data) {
       assertEquals(data, myOperations.multiply(1, data), "Multiplication with 1 not giving passed number");
    }

    @ParameterizedTest
    @ValueSource(strings = {"data","bytes"})
    @DisplayName("Parameter Operation Test from Value")
    public void givenArgumentWhenGreetedThenGreeted(String argument) {
        assertEquals("Hello " + argument, myOperations.greet(argument), () -> "Greeting with argument not greeting properly");
    }

}
