package com.jianli.Junit;
import org.junit.*;

public class TimeTest {
	@Test(timeout = 1000)  
	public void infinity() {  
		while(true);
	}  
}
