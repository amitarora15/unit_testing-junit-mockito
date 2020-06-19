#### Unit Testing Application

Project developed demonstrated various capabilities of using Junit5 and Mockito for Unit Testing applications. 
#
Following capabilities of Unit testing are used
1. Junit5
    1. Before and After (void return type, public and need to be static [all one]) `@BeforeEach`, `@BeforeAll`, `@AfterAll`, `@AfterEach`
    1. Disabled test `@Disabled` or `@Disabled("name")`
    1. Naming Test & Tags `@DisplayName` `@Tag`
    1. Asserts
        1. Same, Equals....   `assertEquals(excpected, actual, optionalmessage)`
        1. Multi assert - `assertAll()`
        1. Throws and Does not throws - `assertThrows()`
        1. Array and List Equals `assertArrayEquals`, `assertIterableEquals`
        1. Timeout & Timeout Preemptively - `assertTimeout`, `assertTimeoutPreemptively`
    1. Repeated Test - `@RepeatedTest(value =5, name = "{displayName} -> {currentRepetition}")`
    1. Nested Test - `@Nested`
    1. Assumptions - `assumeTrue assumeFalse assumeAll`
    1. Dynamic Test - `@TestFactory` -> @BeforeEach/@AfterEach will be called once for all elements
    1. ParameterizedTest - `@ParameterizedTest` & junit-jupiter-params -> @BeforeEach/@AfterEach will be called every times
        1. `@ValueSource(ints = {6,8,2,9})`
        1. `@ValueSource(ints = {6,8,2,9})`
        1. `@CsvSource({ "foo, 1", "'baz, qux', 3" })`
    1. TestInstance - `@TestInstance(Lifecycle.PER_CLASS)` - use same instance of all test methods
1. build.gradle  
     ```groovy
    dependencies {
         testCompile('org.junit.jupiter:junit-jupiter-api:5.3.0')
         testCompile('org.junit.jupiter:junit-jupiter-params:5.3.0')
         testRuntime('org.junit.jupiter:junit-jupiter-engine:5.3.0')
   
         testCompile group:  'org.mockito', name : 'mockito-core', version : '2.19.0'
         testCompile group:  'org.mockito', name : 'mockito-junit-jupiter', version : '2.19.0'
     }
      
     test {
         useJUnitPlatform()
         testLogging {
             events "passed", "skipped", "failed"
         }
     }   
1. Mockito
    1. Test Replacement or test doubles is way of replacing dependencies and can be achieved using:
        1. Dummy object - passed as parameter in test method, but not used in code under test
        1. Fake objects - In memory test
        1. Stubs - Generally for behaviour (partial implementation)
        1. Mocks - Dummy implementation
    1. the class to be tested should avoid any hard dependency on external data.
    1. To use Mockito in your test case, use - `@ExtendWith(MockitoExtension.class)` on class
    1. To create a mock bean, annotate it with `@Mock` or `Mockito.mock()`
    1. To use strict settings, use `@MockitoSettings(strictness = Strictness.WARN)` on class or can be defined used `Mockito.lenient()` for each verify/when invocation
        1. test fails early when a stubbed method gets called with different arguments than what it was configured for (with PotentialStubbingProblem exception).
        1. test fails when a stubbed method isn’t called (with UnnecessaryStubbingException exception).
        1. org.mockito.Mockito.verifyNoMoreInteractions(Object) also verifies that all stubbed methods have been called during the test
    1. If don't define return value of method in mock class, then it will return empty (0, null, empty, false)
    1. `when(test.getUniqueId()).thenReturn(43);` anyString() or anyInt() or isA(X.class) can be used
    1. `when(properties.get(”Anddroid”)).thenThrow(new IllegalArgumentException(...));`
    1. `doReturn(“42”).when(spyProperties).get(”shoeSize”);` or doThrowWhen used for spying 
    1. `doAnswer(returnsFirstArg()).when(list).add(anyString());`  or   `when(list.add(anyString())).thenAnswer(returnsFirstArg());` or `when(list.add(anyString())).then(returnsFirstArg());`
    1.  @Spy or the spy() method can be used to wrap a real object. Every call, unless specified otherwise, is delegated to the object. doReturn is used here
    1. Behaviour testing - `verify(test, times(2)).getUniqueId();` or `verify(test).testing(ArgumentMatchers.eq(12));` and in end you can call `verifyNoMoreInteractions(test);`. You can use anyString() or any(A.class) for parameters
    1. @InjectMocks - used for injecting mocks in test object
    1. ArgumentCaptor - `@Captor` then use  captor.capture() to capture parameter and captor.getValue() to get value of parameter
    1. To activate the mocking of final classes create the file org.mockito.plugins.MockMaker in either src/test/resources/mockito-extensions/ or src/mockito-extensions/. Add this line to the file: mock-maker-inline
    1. you cannot mock static methods and private methods.   

---
Important Points    
1. You should write software tests for the critical and complex parts of your application. In general it it safe to ignore trivial code.
1. Each class should have a corresponding test class named - [classname]Test.
1. Name of test method:
    1. a test name should explain what the test does.
    1. One possible convention is to use the "should" in the test method name
    1. Other is to use "Given[ExplainYourInput]When[WhatIsDone]Then[ExpectedResult]"
1. JUnit 5 consists of three projects (JUnit Platform, JUnit Jupiter, and JUnit Vintage)
1. Import following dependencies - junit-jupiter-api & junit-jupiter-engine
1. Runs >= JDK 1.8
1. Optional message can be evaluated conditionally by passing Supplier<T> through Lambda lile () -> {}
1. For each test method, Java creates a new instance of the testing class.
1. Bad idea - Global variables & expectation of testing order
1. Holding the data set in a Collection and iterating over it with the assertion in the loop body has the problem that the first assertion failure will stop the test execution. Thats why use Dynamic Test, Repeated Tests or Parameterized Test
1. Static Import
```java
   import static org.mockito.Mockito.*;
   import static org.mockito.AdditionalAnswers.*;
   import static org.junit.jupiter.api.Assertions.*;
```
---    
_References_
>* https://stackabuse.com/unit-testing-in-java-with-junit-5/
>* https://www.vogella.com/tutorials/JUnit/article.html    
    
