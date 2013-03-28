package dverbovskiy.json;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: dverbovskiy
 * Date: 24.03.13
 * Time: 21:41
 */
public class JSONPathTest {
    @Rule
    public TestName name = new TestName();

    @Test(expected = IllegalArgumentException.class)
    public void testRoot_Digit() throws Exception {
        new JSONPath("7");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() throws Exception {
        new JSONPath("");
    }

    @Test
    public void testSimple_1() throws Exception {
        JSONPath json = new JSONPath("a");

        assertEquals(json.getKey(0), "a");
        assertEquals(json.getType(0), JSONPathType.FIELD);
    }

    @Test
    public void testSimple_2() throws Exception {
        JSONPath json = new JSONPath("a.b");

        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add("a");
        expectedKeys.add("b");

        List<String> actualKeys = new ArrayList<String>();
        actualKeys.add(json.getKey(0));
        actualKeys.add(json.getKey(1));
        //============================
        List<JSONPathType> expectedTypes = new ArrayList<JSONPathType>();
        expectedTypes.add(JSONPathType.OBJECT);
        expectedTypes.add(JSONPathType.FIELD);

        List<JSONPathType> actualTypes = new ArrayList<JSONPathType>();
        actualTypes.add(json.getType(0));
        actualTypes.add(json.getType(1));

        assertEquals(expectedKeys, actualKeys);
        assertEquals(expectedTypes, actualTypes);
    }

    @Test
    public void testSimple_3() throws Exception {
        JSONPath json = new JSONPath("a.b.c");

        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add("a");
        expectedKeys.add("b");
        expectedKeys.add("c");

        List<String> actualKeys = new ArrayList<String>();
        actualKeys.add(json.getKey(0));
        actualKeys.add(json.getKey(1));
        actualKeys.add(json.getKey(2));
        //============================
        List<JSONPathType> expectedTypes = new ArrayList<JSONPathType>();
        expectedTypes.add(JSONPathType.OBJECT);
        expectedTypes.add(JSONPathType.OBJECT);
        expectedTypes.add(JSONPathType.FIELD);

        List<JSONPathType> actualTypes = new ArrayList<JSONPathType>();
        actualTypes.add(json.getType(0));
        actualTypes.add(json.getType(1));
        actualTypes.add(json.getType(2));

        assertEquals(expectedKeys, actualKeys);
        assertEquals(expectedTypes, actualTypes);
    }


    @Test
    public void testArray_1() throws Exception {
        JSONPath json = new JSONPath("a.2");

        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add("a");
        expectedKeys.add("2");

        List<String> actualKeys = new ArrayList<String>();
        actualKeys.add(json.getKey(0));
        actualKeys.add(json.getKey(1));
        //============================
        List<JSONPathType> expectedTypes = new ArrayList<JSONPathType>();
        expectedTypes.add(JSONPathType.ARRAY);
        expectedTypes.add(JSONPathType.ELEMENT_FIELD);

        List<JSONPathType> actualTypes = new ArrayList<JSONPathType>();
        actualTypes.add(json.getType(0));
        actualTypes.add(json.getType(1));

        assertEquals(expectedKeys, actualKeys);
        assertEquals(expectedTypes, actualTypes);
    }

    @Test
    public void testArray_2() throws Exception {
        JSONPath json = new JSONPath("a.b.2");

        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add("a");
        expectedKeys.add("b");
        expectedKeys.add("2");

        List<String> actualKeys = new ArrayList<String>();
        actualKeys.add(json.getKey(0));
        actualKeys.add(json.getKey(1));
        actualKeys.add(json.getKey(2));
        //============================
        List<JSONPathType> expectedTypes = new ArrayList<JSONPathType>();
        expectedTypes.add(JSONPathType.OBJECT);
        expectedTypes.add(JSONPathType.ARRAY);
        expectedTypes.add(JSONPathType.ELEMENT_FIELD);

        List<JSONPathType> actualTypes = new ArrayList<JSONPathType>();
        actualTypes.add(json.getType(0));
        actualTypes.add(json.getType(1));
        actualTypes.add(json.getType(2));

        assertEquals(expectedKeys, actualKeys);
        assertEquals(expectedTypes, actualTypes);
    }

    @Test
    public void testArray_3() throws Exception {
        JSONPath json = new JSONPath("a.b.2.c");

        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add("a");
        expectedKeys.add("b");
        expectedKeys.add("2");
        expectedKeys.add("c");

        List<String> actualKeys = new ArrayList<String>();
        actualKeys.add(json.getKey(0));
        actualKeys.add(json.getKey(1));
        actualKeys.add(json.getKey(2));
        actualKeys.add(json.getKey(3));
        //============================
        List<JSONPathType> expectedTypes = new ArrayList<JSONPathType>();
        expectedTypes.add(JSONPathType.OBJECT);
        expectedTypes.add(JSONPathType.ARRAY);
        expectedTypes.add(JSONPathType.ELEMENT);
        expectedTypes.add(JSONPathType.FIELD);

        List<JSONPathType> actualTypes = new ArrayList<JSONPathType>();
        actualTypes.add(json.getType(0));
        actualTypes.add(json.getType(1));
        actualTypes.add(json.getType(2));
        actualTypes.add(json.getType(3));

        assertEquals(expectedKeys, actualKeys);
        assertEquals(expectedTypes, actualTypes);
    }

    @Test
    public void testArray_4() throws Exception {
        JSONPath json = new JSONPath("a.b.2.2.c");

        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add("a");
        expectedKeys.add("b");
        expectedKeys.add("2");
        expectedKeys.add("2");
        expectedKeys.add("c");

        List<String> actualKeys = new ArrayList<String>();
        actualKeys.add(json.getKey(0));
        actualKeys.add(json.getKey(1));
        actualKeys.add(json.getKey(2));
        actualKeys.add(json.getKey(3));
        actualKeys.add(json.getKey(4));
        //============================
        List<JSONPathType> expectedTypes = new ArrayList<JSONPathType>();
        expectedTypes.add(JSONPathType.OBJECT);
        expectedTypes.add(JSONPathType.ARRAY);
        expectedTypes.add(JSONPathType.ELEMENT_ARRAY);
        expectedTypes.add(JSONPathType.ELEMENT);
        expectedTypes.add(JSONPathType.FIELD);

        List<JSONPathType> actualTypes = new ArrayList<JSONPathType>();
        actualTypes.add(json.getType(0));
        actualTypes.add(json.getType(1));
        actualTypes.add(json.getType(2));
        actualTypes.add(json.getType(3));
        actualTypes.add(json.getType(4));

        assertEquals(expectedKeys, actualKeys);
        assertEquals(expectedTypes, actualTypes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTricky_1() throws Exception {
        new JSONPath("a.-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTricky_2() throws Exception {
        new JSONPath(".a");
    }

    @Test
    public void testTricky_3() throws Exception {
        JSONPath json = new JSONPath("a.");

        assertEquals(json.getKey(0), "a");
        assertEquals(json.getType(0), JSONPathType.FIELD);
        assertEquals(json.length(), 1);
    }

    @Test
    public void testArray_5() throws Exception {
        JSONPath json = new JSONPath("a.2.2");

        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add("a");
        expectedKeys.add("2");
        expectedKeys.add("2");

        List<String> actualKeys = new ArrayList<String>();
        actualKeys.add(json.getKey(0));
        actualKeys.add(json.getKey(1));
        actualKeys.add(json.getKey(2));
        //============================
        List<JSONPathType> expectedTypes = new ArrayList<JSONPathType>();
        expectedTypes.add(JSONPathType.ARRAY);
        expectedTypes.add(JSONPathType.ELEMENT_ARRAY);
        expectedTypes.add(JSONPathType.ELEMENT_FIELD);

        List<JSONPathType> actualTypes = new ArrayList<JSONPathType>();
        actualTypes.add(json.getType(0));
        actualTypes.add(json.getType(1));
        actualTypes.add(json.getType(2));

        assertEquals(expectedKeys, actualKeys);
        assertEquals(expectedTypes, actualTypes);
    }

}
