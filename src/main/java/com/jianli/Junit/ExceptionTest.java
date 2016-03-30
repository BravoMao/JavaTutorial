package com.jianli.Junit;

import org.junit.*;

public class ExceptionTest {
	/**
	 * In above example, the divisionWithException() method will throw an
	 * ArithmeticException Exception, since this is an expected exception, so
	 * the unit test will pass.
	 */
	
	@Test(expected = ArithmeticException.class)
	public void divisionWithException() {
		System.out.println("divisionWithException running");
		int i = 1 / 0;
	}
}
