package com.jianli.Junit;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BasicUsage.class,
        ExceptionTest.class
})

//The below example means both unit test JunitTest1 and JunitTest2 will run together after JunitTest5 is executed.

public class SuiteTest {

}
