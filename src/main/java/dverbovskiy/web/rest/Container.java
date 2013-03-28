package dverbovskiy.web.rest;

import dverbovskiy.util.Util;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * This container is the highest level of processing json data containers
 */
public class Container {

    public static final String CMD = "cmd";
    public static final String DATA = "data";
    public static final String OPTIONS = "options";
    private static final Logger log = Logger.getLogger(Container.class);
    private JSONObject box = new JSONObject();

    private Container() {
        super();
    }

    public static Container getInstance(String input) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(input);

        Object cmd = getMandatory(jsonObject, CMD);
        Object data = getMandatory(jsonObject, DATA);
        Object options = getMandatory(jsonObject, OPTIONS);

        return getInstance(cmd, data, options);
    }

    public static Container getInstance() throws Exception {
        return getInstance("", new JSONObject(), new JSONObject());
    }

    private static Container getInstance(Object cmd, Object data, Object options) throws Exception {
        Container container = new Container();

        container.box.put(CMD, cmd);
        container.box.put(DATA, data);
        container.box.put(OPTIONS, options);
        return container;
    }

    private static Object getMandatory(JSONObject jsonObject, String label) throws IllegalArgumentException {
        Object buffer;

        if (!(CMD.equals(label) || DATA.equals(label) || OPTIONS.equals(label))) {
            throw new IllegalArgumentException("\"" + label + "\" is unknown parameter");
        }

        if (jsonObject.containsKey(label)) {
            buffer = jsonObject.get(label);
        } else {
            throw new IllegalArgumentException("\"" + label + "\" parameter is mandatory, even if it is empty");
        }
        return buffer;
    }

    @Override
    public String toString() {
        return box.toJSONString();
    }

    public Object get(String fullPath) {
        Object node = this.box;
        fullPath = fullPath.toLowerCase();
        log.debug("fullPath: " + fullPath);

        for (String path : fullPath.split("\\.")) {
            if (node == null) {
                break;
            }

            if (node instanceof JSONArray) {
                node = ((JSONArray) node).get(Integer.parseInt(path));
            } else {
                node = ((JSONObject) node).get(path);
            }
        }
        return node;
    }

    public Object put(String fullPath, Object value) throws Exception {
        Object grandParent = this.box;  // needed when reCasting is done
        Object parent = this.box;
        Object previousValue = null;



        String[] pathParts = fullPath.toLowerCase().split("\\.");

        if (!validatePath(pathParts)) {
            return null;
        }
        for (int i = 0; i < pathParts.length; i++) {
            String path = pathParts[i];
            boolean isInt = Util.isInteger(path);
            boolean isLast = (i + 1 == pathParts.length) ? true : false;
            int index = -1;

            if (isInt) {
                index = Integer.parseInt(path);

                if (index < 0) {
                    log.warn("Negative array index:" + index + ". It's not effective.");
                    index = Math.abs(index);
                }
            }

            if (parent instanceof JSONArray) {
                JSONArray node = (JSONArray) parent;

                if (isInt) {
                    if (!node.contains(index)) {
                        // adding elements for parent[index]
                        int size = node.size();

                        if (size < index) {
                            log.warn("Not proper order of adding values. It's not effective. Current max index: " + (size - 1) + "; index : " + index);
                        }
                        for (; size <= index; size++) {
                            node.add(new JSONObject()); // adding empty object to array
                        }
                    }
                    previousValue = putOrGoDeeperJSONArray(grandParent, parent, node, isLast, index, value);
                } else {
                    // implying that user wants to work with array[0] element
                    if (!node.contains(0)) {
                        // TODO: verify this case
                        node.add(0, new JSONObject());
                    }
                    JSONObject element = (JSONObject) node.get(0);

                    if (!element.containsKey(path)) {
                        // TODO: verify this case
                        element.put(path, new JSONObject()); // adding empty object to array[0] element
                    }
                    previousValue = putOrGoDeeperJSONObject(grandParent, parent, element, isLast, path, value);
                }
            } else if (parent instanceof JSONObject) {
                JSONObject element = (JSONObject) parent;

                if (isInt) {
                    // TODO Создаем JSONArray. В JSONArray[0] ложим текущее СОДЕРЖИМОЕ element(он же parent) JSONObj, а
                    // его удаляем, создавая с таким же именем
                    int r = 78;
                    JSONArray array = new JSONArray();

                    array.add(0, element);
                    if (index == 0) {
                        // TODO а вдруг за этим 0 больше ничего нет, тогда придется жертвовать или element, или value
                    } else {
                        array.add(0, element);
                        log.warn("Not proper order of adding values. It's not effective. Current max index: 1; index : " + index);

                        for (int j = 1; j <= index; j++) {
                            array.add(j, new JSONObject()); // adding empty object to array
                        }
                    }


                } else {
                    if (!element.containsKey(path)) {
                        element.put(path, new JSONObject());
                    }
                }
                previousValue = putOrGoDeeperJSONObject(grandParent, parent, element, isLast, path, value);
            } else {
                // TODO: verify this case
                throw new Exception("Unknown type of parent object: " + parent.getClass());
            }
        }
        return previousValue;
    }

    private Object putOrGoDeeperJSONArray(Object grandParent, Object parent, JSONArray array, boolean isLast, int index, Object value) {
        if (!isLast) {
            grandParent = parent;
            parent = array.get(index);   // going deeper
            return null;
        }
        return array.set(index, value);
    }

    private Object putOrGoDeeperJSONObject(Object grandParent, Object parent, JSONObject object, boolean isLast, String key, Object value) {
        if (!isLast) {
            grandParent = parent;
            parent = object.get(key);   // going deeper
            return null;
        }
        return object.put(key, value);
    }

    private boolean validatePath(String[] path) throws IllegalArgumentException {
        if (CMD.equals(path[0])) {
            if (path.length > 1) {
                throw new IllegalArgumentException("\"" + CMD + "\" can contain only string value");
            }
        } else if (DATA.equals(path[0]) || OPTIONS.equals(path[0])) {
            if (path.length > 1 && Util.isInteger(path[1])) {
                throw new IllegalArgumentException("Neither \"" + DATA + "\" nor \"" + OPTIONS + "\" can contain only string value");
            }
        }
        return true;
    }
}
