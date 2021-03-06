package com.jianli.Junit;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ParameterizedTest {
	private int numberA;
	private int numberB;
	private int expected;

	//parameters pass via this constructor
	public  ParameterizedTest(int numberA, int numberB, int expected) {
		this.numberA = numberA;
		this.numberB = numberB;
		this.expected = expected;
	}

	//Declares parameters heren
	@Parameters(name = "{index}: add({0}+{1})={2}") // just used to have a meanningful test name
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			{ 1, 1, 2 }, 
			{ 2, 2, 4 }, 
			{ 8, 2, 10 }, 
			{ 4, 5, 9 } 
		});
	}

	@Test
	public void test_add() {	
		assertEquals(expected,MathUtils.add(numberA, numberB));
	}

}
