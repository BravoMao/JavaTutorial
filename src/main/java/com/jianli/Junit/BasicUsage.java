package com.jianli.Junit;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class BasicUsage {
	/**
	 * The code marked @Before is executed before each test, while @BeforeClass
	 * runs once before the entire test fixture. If your test class has ten
	 * tests, @Before code will be executed ten times, but @BeforeClass will be
	 * executed only once.
	 **/
	private Collection collection;

	@BeforeClass
	public static void oneTimeSetUp() {
		// one-time initialization code
		System.out.println("@BeforeClass - oneTimeSetUp");
	}

	@AfterClass
	public static void oneTimeTearDown() {
		// one-time cleanup code
		System.out.println("@AfterClass - oneTimeTearDown");
	}

	@Before
	public void setUp() {
		collection = new ArrayList();
		System.out.println("@Before - setUp");
	}

	@After
	public void tearDown() {
		collection.clear();
		System.out.println("@After - tearDown");
	}

	@Test
	public void testEmptyCollection() {
		assertTrue(collection.isEmpty());
		System.out.println("@Test - testEmptyCollection");
	}

	@Test
	public void testOneItemCollection() {
		collection.add("itemA");
		assertEquals(1, collection.size());
		System.out.println("@Test - testOneItemCollection");
	}
}
