package dverbovskiy.json;

/**
 * String semi-json input: "data.person.children.0.children.0.4.married"
 * must be marked as:      OBJECT.OBJECT.ARRAY.ELEMENT.ARRAY.ELEMENT_ARRAY.ELEMENT.FIELD
 * User: dverbovskiy
 * Date: 24.03.13
 */
public enum JSONPathType {
    OBJECT, ARRAY, ELEMENT_ARRAY, ELEMENT, ELEMENT_FIELD, FIELD
}
