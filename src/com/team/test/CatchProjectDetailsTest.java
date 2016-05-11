package com.team.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.team.catchdata.CatchProjectDetails;

public class CatchProjectDetailsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCatchHTMLContent() throws IOException {
		assertTrue(CatchProjectDetails.catchHTMLContent().size() > 0);
	}

}
