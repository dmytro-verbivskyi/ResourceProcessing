package dverbovskiy.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.perf4j.StopWatch;

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
        assertEquals("{\"a\":[null,null,1]}", json.toString());
    }

    @Test
    public void testPut_Array3() throws Exception {
        JSONObj json = JSONObj.getInstance();
        json.put("a.1.2", 1);
        assertEquals("{\"a\":[null,[null,null,1]]}", json.toString());
    }

    @Test
    public void testPut_ArrayProperty1() throws Exception {
        JSONObj json = JSONObj.getInstance();
        json.put("a.1.b", 1);
        assertEquals("{\"a\":[null,{\"b\":1}]}", json.toString());
    }

    @Test
    public void testMega() throws Exception {
        JSONObj json = bigStructure();
        assertEquals(json.toString(), "{\"cmd\":\"createPerson\",\"data\":{\"person\":{\"married\":true,\"name\":\"Pupkin Ivan Ivanovich\",\"age\":59,\"children\":[{\"married\":true,\"name\":\"Pupkin Nikolay Ivanovich\",\"age\":34,\"children\":[{\"married\":false,\"name\":\"Pupkin Petr Nikolayevich\",\"age\":9,\"children\":[]},{\"married\":false,\"name\":\"Pupkin Nikolay Nikolayevich\",\"age\":4,\"children\":[]}]},{\"married\":false,\"name\":\"Pupkin Nikolay Nikolayevich\",\"age\":19,\"children\":[]}]}},\"options\":null}");
    }

    @Ignore("It's not a test, don't run it each time.")
    @Test
    public void benchMark_PUT() throws Exception {
        /*
        MAX = 1000000   myWay[21253, 21479] / oldWay[693, 664]      = 31.4 хуже
        MAX = 10000     myWay[214, 249]     / oldWay[6, 6]          = 38.5 хуже
        MAX = 100       myWay[60, 55]       / oldWay[1, 2]          = 38.3 хуже
        */
        int MAX = 10000;
        StopWatch timer = new StopWatch();

        // прогревочный забег, хотя это и странно
        for (int i = 0; i < MAX; i++) bigStructure();
        for (int i = 0; i < MAX; i++) bigStructureOldWay();

        // ================== MY way ==================
        timer.start();
        for (int i = 0; i < MAX; i++) bigStructure();
        timer.stop();
        System.out.println(timer.getElapsedTime());


        // ================== OLD way ==================
        timer.start();
        for (int i = 0; i < MAX; i++) bigStructureOldWay();
        timer.stop();
        System.out.println(timer.getElapsedTime());

        timer.start();
        for (int i = 0; i < MAX; i++) bigStructureOldWay();
        timer.stop();
        System.out.println(timer.getElapsedTime());

        // ================== MY way ==================
        timer.start();
        for (int i = 0; i < MAX; i++) bigStructure();
        timer.stop();
        System.out.println(timer.getElapsedTime());
    }

    private JSONObj bigStructure() throws Exception {
        JSONObj json = JSONObj.getInstance();
        json.put("cmd", "createPerson");
        json.put("data.person.married", true);
        json.put("data.person.age", 59);
        json.put("data.person.name", "Pupkin Ivan Ivanovich");
        json.put("data.person.children.0.married", true);
        json.put("data.person.children.0.age", 34);
        json.put("data.person.children.0.name", "Pupkin Nikolay Ivanovich");
        json.put("data.person.children.0.children.0.married", false);
        json.put("data.person.children.0.children.0.age", 9);
        json.put("data.person.children.0.children.0.name", "Pupkin Petr Nikolayevich");
        json.put("data.person.children.0.children.0.children", new JSONArray());
        json.put("data.person.children.0.children.1.married", false);
        json.put("data.person.children.0.children.1.age", 4);
        json.put("data.person.children.0.children.1.name", "Pupkin Nikolay Nikolayevich");
        json.put("data.person.children.0.children.1.children", new JSONArray());
        json.put("data.person.children.1.married", false);
        json.put("data.person.children.1.age", 19);
        json.put("data.person.children.1.name", "Pupkin Nikolay Nikolayevich");
        json.put("data.person.children.1.children", new JSONArray());
        json.put("options", null);
        return json;
    }

    private JSONObject bigStructureOldWay() throws Exception {
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
        return container;
    }
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
