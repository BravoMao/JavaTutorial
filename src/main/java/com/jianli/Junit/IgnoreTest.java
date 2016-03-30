package com.jianli.Junit;
import org.junit.*;
public class IgnoreTest {
	
	@Ignore("Not Ready to Run")  
	@Test
	public void notRunTest() {  
	  System.out.println("Method is not ready yet");
	}
	
	@Test
	public void runTest() {  
	  System.out.println("This method is running");
	}  
}
