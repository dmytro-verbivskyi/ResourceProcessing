package dverbovskiy.json;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.assertEquals;

/**
 * User: dverbovskiy
 * Date: 24.03.13
 * Time: 18:56
 */
public class JSONObjTest {
    @Rule
    public TestName name = new TestName();

    @Test
    public void testGetInstance_Empty() throws Exception {
        JSONObj json = JSONObj.getInstance();
        assertEquals("{}", json.toString());
    }

    @Test
    public void testGetInstance_MinimalObject() throws Exception {
        JSONObj json = JSONObj.getInstance("{}");
        assertEquals("{}", json.toString());
    }

    @Test
    public void testPut_Simple1() throws Exception {
        JSONObj json = JSONObj.getInstance();
        json.put("a", 1);
        assertEquals("{\"a\":1}", json.toString());
    }

    @Test
    public void testPut_Simple2() throws Exception {
        JSONObj json = JSONObj.getInstance();
        json.put("a.b", 1);
        assertEquals("{\"a\":{\"b\":1}}", json.toString());
    }

    @Test
    public void testPut_Simple3() throws Exception {
        JSONObj json = JSONObj.getInstance();
        json.put("a.b.c", 1);
        assertEquals("{\"a\":{\"b\":{\"c\":1}}}", json.toString());
    }

    @Test
    public void testPut_Array1() throws Exception {
        JSONObj json = JSONObj.getInstance();
        json.put("a.0", 1);
        assertEquals("{\"a\":[1]}", json.toString());
    }

    @Test
    public void testPut_Array2() throws Exception {
        JSONObj json = JSONObj.getInstance();
        json.put("a.2", 1);
        assertEquals("{\"a\":[{},{},1]}", json.toString());
    }

    @Test
    public void testPut_Array3() throws Exception {
        JSONObj json = JSONObj.getInstance();
        json.put("a.2.2", 1);
        assertEquals("{\"a\":[{},{},1]}", json.toString());
    }
}
