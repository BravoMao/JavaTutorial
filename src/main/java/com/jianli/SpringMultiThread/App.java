package com.jianli.SpringMultiThread;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jianli.SpringMultiThread.config.AppConfig;
import com.jianli.SpringMultiThread.thread.PrintTask2;
import com.jianli.SpringMultiThread.thread.PrintThread;

public class App {
	public static void main(String[] args) {
		//Case 1:Spring + Java Threads example
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		PrintThread printThread1 = (PrintThread) ctx.getBean("printThread");
		printThread1.setName("Thread 1");

		PrintThread printThread2 = (PrintThread) ctx.getBean("printThread");
		printThread2.setName("Thread 2");

		PrintThread printThread3 = (PrintThread) ctx.getBean("printThread");
		printThread3.setName("Thread 3");

		PrintThread printThread4 = (PrintThread) ctx.getBean("printThread");
		printThread4.setName("Thread 4");

		PrintThread printThread5 = (PrintThread) ctx.getBean("printThread");
		printThread5.setName("Thread 8");

		PrintThread printThread6 = (PrintThread) ctx.getBean("printThread");
		printThread5.setName("Thread 6");

		printThread1.start();
		printThread2.start();
		printThread3.start();
		printThread4.start();
		printThread5.start();
		printThread6.start();
		
		//Case 2:Spring Thread Pool + Spring non-managed bean example(using xml)
		/*
		 * ApplicationContext context = new ClassPathXmlApplicationContext(
		 * "Spring-Config.xml");
		 * 
		 * ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor)
		 * context .getBean("taskExecutor"); taskExecutor.execute(new PrintTask(
		 * "Thread 1")); taskExecutor.execute(new PrintTask("Thread 2"));
		 * taskExecutor.execute(new PrintTask("Thread 3"));
		 * taskExecutor.execute(new PrintTask("Thread 4"));
		 * taskExecutor.execute(new PrintTask("Thread 5"));
		 * 
		 * // check active thread, if no then shut down the thread pool for (;;)
		 * { int count = taskExecutor.getActiveCount(); System.out.println(
		 * "Active Threads : " + count); try { Thread.sleep(1000); } catch
		 * (InterruptedException e) { e.printStackTrace(); } if (count == 0) {
		 * taskExecutor.shutdown(); break; } }
		 */
		
		//Case: 3:Spring Thread Pool + Spring managed bean example
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
		for (Integer i = 0; i < 100; i++) {
			PrintTask2 printTask = (PrintTask2) context.getBean("printTask2");
			printTask.setName("Thread " + i.toString());
			taskExecutor.execute(printTask);

		//to check active thread, if no then shut down the thread pool 
			for (;;) {
				int count = taskExecutor.getActiveCount();
				System.out.println("Active Threads : " + count);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (count == 0) {
					taskExecutor.shutdown();
					break;
				}
			}

		}
	}
}
