package dverbovskiy.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("@Before - UtilTest.eachTimeSetup");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("@After - UtilTest.eachTimeTearDown");
    }

    @Test
    public void testIsInteger() throws Exception {
        System.out.println("UtilTest.testIsInteger()");
    }

    /*@Test
    public void testIsInteger() throws Exception {

    }*/

    @Test
    public void testAs() throws Exception {
        System.out.println("UtilTest.testAs()");
    }
}
