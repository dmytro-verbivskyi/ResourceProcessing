package dverbovskiy.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * User: dverbovskiy
 * Date: 24.03.13
 * Time: 18:45
 */
public class JSONObj {
    private JSONObject box = new JSONObject();

    private JSONObj() {
        super();
    }

    public static JSONObj getInstance(String input) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(input);

        JSONObj jO = new JSONObj();
        jO.box = jsonObject;
        return jO;
    }

    public static JSONObj getInstance() throws Exception {
        return new JSONObj();
    }

    @Override
    public String toString() {
        return box.toJSONString();
    }

    @Override
    public int hashCode() {
        return box.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JSONObj)) return false;

        JSONObj that = (JSONObj) o;
        return box.equals(that.box);
    }


    public Object put(String fullPath, Object value) throws Exception {
        Object grandParent = this.box;
        Object parent = this.box;
        Object previousValue = null;

        JSONPath path = new JSONPath(fullPath);

        for (int i = 0; i < path.length(); i++) {
            JSONPathType type = path.getType(i);
            String key = path.getKey(i);

            switch (type) {
                case OBJECT: {
                    JSONObject jO = (JSONObject) parent;

                    if (!jO.containsKey(key)) {
                        jO.put(key, new JSONObject());
                    } // and then go deeper
                    grandParent = parent;
                    parent = jO.get(key);
                }
                break;
                case ARRAY: {
                    JSONObject jO = (JSONObject) parent;

                    if (!jO.containsKey(key)) {
                        jO.put(key, new JSONArray());
                    }
                    grandParent = parent;
                    parent = jO.get(key);
                }
                break;
                case ELEMENT_ARRAY: {
                }
                break;
                case ELEMENT:
                case ELEMENT_FIELD: {
                    JSONArray jO = (JSONArray) parent;
                    int index = Integer.parseInt(key);

                    if (jO.size() <= index) {
                        for (int j = 0; j <= index; j++) {
                            jO.add(j, new JSONObject());
                        }
                    }
                    if (type == JSONPathType.ELEMENT) {
                        grandParent = parent;
                        parent = jO.get(index);
                    } else {
                        previousValue = jO.set(index, value);
                    }
                }
                break;
                case FIELD: {
                    JSONObject jO = (JSONObject) parent;
                    previousValue = jO.put(key, value);
                }
                break;
                default: {
                }
                break;
            }
        }
        return previousValue;
    }


}


