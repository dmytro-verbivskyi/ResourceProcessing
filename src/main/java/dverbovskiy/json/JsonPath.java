package dverbovskiy.json;

import dverbovskiy.util.Util;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * String semi-json input: "data.person.children.0.children.0.4.married"
 * must be marked as:      OBJECT.OBJECT.ARRAY.ELEMENT.ARRAY.ELEMENT_ARRAY.ELEMENT.FIELD
 * "data.0" >> ARRAY.ELEMENT_FIELD
 * User: dverbovskiy
 * Date: 24.03.13
 * Time: 19:53
 */
class JSONPath {
    private List<PathPiece> path = new ArrayList<PathPiece>();

    JSONPath(String fullPath) throws IllegalArgumentException {
        String[] pathParts = fullPath.toLowerCase().split("\\.");

        if (0 == pathParts[0].length()) {
            throw new IllegalArgumentException("Path cannot be empty");
        }
        JSONPathType type = JSONPathType.OBJECT;
        int i = 0;

        for (; i < pathParts.length; i++) {
            boolean isInt = Util.isInteger(pathParts[i]);
            if (i == 0 && isInt) {
                throw new IllegalArgumentException("Root cannot be of type ELEMENT");
            }

            if (isInt) {
                if (0 > Integer.parseInt(pathParts[i])) {
                    throw new IllegalArgumentException("Negative array index not allowed");
                }

                if (type == JSONPathType.ELEMENT) {
                    // editing/marking preLast ELEMENT as an ELEMENT_ARRAY
                    path.get(i - 1).pathType = JSONPathType.ELEMENT_ARRAY;
                    type = JSONPathType.ELEMENT;
                } else {
                    // editing/marking preLast OBJECT as an ARRAY
                    path.get(i - 1).pathType = JSONPathType.ARRAY;
                    type = JSONPathType.ELEMENT;
                }
            } else {
                type = JSONPathType.OBJECT;
            }
            path.add(new PathPiece(pathParts[i], type));
        }
        // modifying the last element type
        switch (path.get(i - 1).pathType) {
            case OBJECT: {
                path.get(i - 1).pathType = JSONPathType.FIELD;
            }
            break;
            case ELEMENT: {
                path.get(i - 1).pathType = JSONPathType.ELEMENT_FIELD;
            }
            break;
            default: {
                throw new NotImplementedException();
            }
        }
    }

    public String getKey(int index) {
        return path.get(index).pathKey;
    }

    public JSONPathType getType(int index) {
        return path.get(index).pathType;
    }

    public int length() {
        return path.size();
    }

    private class PathPiece {
        String pathKey;
        JSONPathType pathType;

        PathPiece(String p, JSONPathType v) {
            pathKey = p;
            pathType = v;
        }
    }
}
