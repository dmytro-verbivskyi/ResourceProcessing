package dverbovskiy.web.rest;

import dverbovskiy.util.DebugUtil;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TestName;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ContainerTest {
    @Rule
    public TestName name = new TestName();
    private static String emptyContainer = "{\"cmd\":\"\",\"data\":{},\"options\":{}}";
    private static String CMD = Container.CMD;
    private static String DATA = Container.DATA;
    private static String OPTIONS = Container.OPTIONS;

    @Before
    public void setUp() throws Exception {
        System.out.println(DebugUtil.start(name.getMethodName()));
    }

    @After
    public void tearDown() throws Exception {
        //System.out.println(DebugUtil.end(name.getMethodName()));
    }

    @Test
    public void testGetInstance_Empty() throws Exception {
        Container container = Container.getInstance();
        assertEquals(emptyContainer, container.toString());
    }

    @Test
    public void testGetInstance_MinimalEmpty() throws Exception {
        Container container = Container.getInstance(emptyContainer);
        assertEquals(emptyContainer, container.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInstance_MandatoryCmd() throws Exception {
        String wrong = emptyContainer.replace(CMD, "wrong");
        Container.getInstance(wrong);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInstance_MandatoryData() throws Exception {
        String wrong = emptyContainer.replace(DATA, "wrong");
        Container.getInstance(wrong);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInstance_MandatoryOptions() throws Exception {
        String wrong = emptyContainer.replace(OPTIONS, "wrong");
        Container.getInstance(wrong);
    }

    @Test
    public void testPut_PreviousCmdEmpty() throws Exception {
        Container container = Container.getInstance();
        Object emptyStr = container.put(CMD, "text");
        assertEquals(emptyStr.toString(), "");
    }

    @Test
    public void testPut_PreviousDataEmpty() throws Exception {
        Container container = Container.getInstance();
        Object emptyStr = container.put(DATA, "text");
        assertEquals(emptyStr, new JSONObject());
    }

    @Test
    public void testPut_PreviousOptionsEmpty() throws Exception {
        Container container = Container.getInstance();
        Object emptyStr = container.put(OPTIONS, "text");
        assertEquals(emptyStr, new JSONObject());
    }

    @Test
    public void testGet_SimpleCmd() throws Exception {
        Container container = Container.getInstance();
        container.put(CMD, "text");
        Object o = container.get(CMD);
        assertEquals(o.toString(), "text");
    }

    @Test
    public void testGet_SimpleData01() throws Exception {
        Container container = Container.getInstance();
        JSONObject jo = new JSONObject();
        jo.put("id", "text");
        container.put(DATA, jo);

        Object o = container.get(DATA);
        assertEquals(o, jo);
    }

    @Test
    public void testGet_SimpleData02() throws Exception {
        Container container = Container.getInstance();
        JSONObject jo = new JSONObject();
        jo.put("id", "text");
        container.put(DATA, jo);

        Object o = container.get(DATA + ".id");
        assertEquals(o.toString(), "text");
    }

    @Test
    public void testPut_PreviousData01() throws Exception {
        Container container = Container.getInstance();
        JSONObject jo = new JSONObject();
        jo.put("id", "text");
        container.put(DATA, jo);

        Object o = container.put(DATA + ".id", "id");
        assertEquals(o.toString(), "text");

        /* // DONE test cases
        container.put("cmd", 123);
        container.put("cmd.id", 123);   // exception
        container.put("cmd.1", 123);    // exception

        container.put("data", 123);
        container.put("data.1", 123);   // exception

        container.put("data.id", 123);*/

        /* // NOT DONE
        container.put("data.id.3", 123);
        container.put("data.id.-3", 123);
        container.put("data.id.3.id", 123);
        container.put("data.id.3.id.9", 123);
        container.put("data.id.3.id", 123);

        // etot case pochti gotov v    container.put("data.id.3", 123);
        container.put("data.id", 123);      // +
        container.put("data.id.3", 123);

        container.put("something.id.3.id.9", 123);

        container.get("cmd");
        container.get("cmd.id");        // null
        container.get("cmd.1");         // null

        container.get("data");
        container.get("data.1");        // null  */
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPut_InvalidCmd01() throws Exception {
        Container container = Container.getInstance();
        container.put(CMD + ".id", "text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPut_InvalidCmd02() throws Exception {
        Container container = Container.getInstance();
        container.put(CMD + ".1", "text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPut_DataCastToArray() throws Exception {
        Container container = Container.getInstance();
        container.put(DATA + ".1", "text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPut_OptionsCastToArray() throws Exception {
        Container container = Container.getInstance();
        container.put(OPTIONS + ".1", "text");
    }

    @Test
    public void testPut_NoSuchPath01() throws Exception {
        Container container = Container.getInstance();
        container.put(DATA + ".id", "text");

        Object o1 = container.get(DATA);
        assertTrue(((JSONObject) o1).containsKey("id"));
        assertTrue(o1 instanceof JSONObject);

        Object o2 = container.get(DATA + ".id");
        assertEquals(o2.toString(), "text");
    }

    @Test
    public void testPut_NoSuchPath02() throws Exception {
        Container container = Container.getInstance();
        container.put(DATA + ".id.3", "text");
        Object o = container.get(DATA);
        int d = 6;
    }

    @Test
    public void testPut_Etalon01() throws Exception {
        JSONObject container = new JSONObject();
        container.put("cmd", "createPerson");
        container.put("options", new JSONObject());

        JSONObject child_1_1 = new JSONObject();
        child_1_1.put("name", "Pupkin Petr Nikolayevich");
        child_1_1.put("age", 9);
        child_1_1.put("married", false);
        child_1_1.put("children", new JSONArray());

        JSONObject child_1_2 = new JSONObject();
        child_1_2.put("name", "Pupkin Nikolay Nikolayevich");
        child_1_2.put("age", 4);
        child_1_2.put("married", false);
        child_1_2.put("children", new JSONArray());

        JSONObject child_1 = new JSONObject();
        child_1.put("name", "Pupkin Nikolay Ivanovich");
        child_1.put("age", 34);
        child_1.put("married", true);
        JSONArray children_1 = new JSONArray();
        children_1.add(child_1_1);
        children_1.add(child_1_2);
        child_1.put("children", children_1);

        JSONObject child_2 = new JSONObject();
        child_2.put("name", "Pupkin Igor Ivanovich");
        child_2.put("age", 19);
        child_2.put("married", false);
        child_2.put("children", new JSONArray());

        JSONObject person = new JSONObject();
        person.put("name", "Pupkin Ivan Ivanovich");
        person.put("age", 59);
        person.put("married", true);
        JSONArray children_0 = new JSONArray();
        children_0.add(child_1);
        children_0.add(child_2);
        person.put("children", children_0);

        JSONObject personContainer = new JSONObject();
        personContainer.put("person", person);

        container.put("data", personContainer);

        String s = container.toJSONString();
        String etalon = "{\"cmd\":\"createPerson\",\"data\":{\"person\":{\"married\":true,\"age\":59,\"name\":\"Pupkin Ivan Ivanovich\",\"children\":[{\"married\":true,\"age\":34,\"name\":\"Pupkin Nikolay Ivanovich\",\"children\":[{\"married\":false,\"age\":9,\"name\":\"Pupkin Petr Nikolayevich\",\"children\":[]},{\"married\":false,\"age\":4,\"name\":\"Pupkin Nikolay Nikolayevich\",\"children\":[]}]},{\"married\":false,\"age\":19,\"name\":\"Pupkin Igor Ivanovich\",\"children\":[]}]}},\"options\":{}}";

        assertEquals(s, etalon);
/*
container.put("cmd", "createPerson");
container.put("data.person.married", true);
container.put("data.person.age", 59);
container.put("data.person.name", "Pupkin Ivan Ivanovich");
container.put("data.person.children.0.married", true);
container.put("data.person.children.0.age", 34);
container.put("data.person.children.0.name", "Pupkin Nikolay Ivanovich");
container.put("data.person.children.0.children.0.married", false);
container.put("data.person.children.0.children.0.age", 9);
container.put("data.person.children.0.children.0.name", "Pupkin Petr Nikolayevich");
container.put("data.person.children.0.children.0.children", null);
container.put("data.person.children.0.children.1.married", false);
container.put("data.person.children.0.children.1.age", 4);
container.put("data.person.children.0.children.1.name", "Pupkin Nikolay Nikolayevich");
container.put("data.person.children.0.children.1.children", null);
container.put("data.person.children.1.married", false);
container.put("data.person.children.1.age", 19);
container.put("data.person.children.1.name", "Pupkin Nikolay Nikolayevich");
container.put("data.person.children.1.children", null);
container.put("options", null);
*/
/*
{
   "cmd":"createPerson",
   "data":{
      "person":{
         "married":true,
         "age":59,
         "name":"Pupkin Ivan Ivanovich",
         "children":[
            {
               "married":true,
               "age":34,
               "name":"Pupkin Nikolay Ivanovich",
               "children":[
                  {
                     "married":false,
                     "age":9,
                     "name":"Pupkin Petr Nikolayevich",
                     "children":[

                     ]
                  },
                  {
                     "married":false,
                     "age":4,
                     "name":"Pupkin Nikolay Nikolayevich",
                     "children":[

                     ]
                  }
               ]
            },
            {
               "married":false,
               "age":19,
               "name":"Pupkin Igor Ivanovich",
               "children":[

               ]
            }
         ]
      }
   },
   "options":{

   }
}
*/
    }
}
