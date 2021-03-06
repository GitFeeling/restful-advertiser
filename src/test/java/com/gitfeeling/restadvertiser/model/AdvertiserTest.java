package com.gitfeeling.restadvertiser.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AdvertiserTest {
	
	private Advertiser expected;
	
	@Before
	public void setup() {
		expected = new Advertiser("DavidOgilvy", "David Ogilvy", 1000);
	}
	
	@Test
	public void testEquals() {
		Advertiser actual = new Advertiser("DavidOgilvy", "David Ogilvy", 1000);
		assertEquals(expected, actual);
	}

	@Test
	public void testEqualsWithNullReturnsFalse() {
		assertNotEquals(expected, null);
	}
	
	@Test
	public void testEqualsWithDifferentClassReturnsFalse() {
		assertNotEquals(expected, "DavidOgilvy");
	}
	
	@Test
	public void testAnyPropertyNotEqualReturnsFalse() {
		Advertiser first = new Advertiser("Dummy", "David Ogilvy", 1000);
		Advertiser second = new Advertiser("DavidOgilvy", "Dummy", 1000);
		Advertiser third = new Advertiser("DavidOgilvy", "David Ogilvy", 2000);
		assertNotEquals(expected, first);
		assertNotEquals(expected, second);
		assertNotEquals(expected, third);
	}

	@Test
	public void testHashCode() {
		Advertiser actual = new Advertiser("DavidOgilvy", "David Ogilvy", 1000);
		assertTrue(expected.hashCode() == actual.hashCode());
		
		expected = new Advertiser("DavidOgilvy", null, 1000);
		actual = new Advertiser("DavidOgilvy", null, 1000);
		assertTrue(expected.hashCode() == actual.hashCode());
	}

}
