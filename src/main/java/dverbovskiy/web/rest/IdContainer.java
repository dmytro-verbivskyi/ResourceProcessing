package dverbovskiy.web.rest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: dverbovskiy
 * Date: 30.01.13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class IdContainer {
    private UUID requestUuid;
    private UUID responseUuid;

    private static final String ID = "id";
    private static final String REQUEST = "requestId";
    private static final String RESPONSE = "responseId";

    public IdContainer() {
        this(UUID.randomUUID(), UUID.randomUUID());
    }

    public IdContainer(UUID requestUuid, UUID responseUuid) {
        super();

        this.requestUuid = requestUuid;
        this.responseUuid = responseUuid;
    }

    public UUID getRequestUuid() {
        return requestUuid;
    }

    public UUID getResponseUuid() {
        return responseUuid;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        JSONObject innerObj = new JSONObject();
        innerObj.put(REQUEST, requestUuid.toString());
        innerObj.put(RESPONSE, responseUuid.toString());

        JSONObject outterObj = new JSONObject();
        outterObj.put(ID, innerObj);

        return outterObj.toJSONString();
    }

    public static IdContainer tryParse(String input) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(input);

            int i = 4;

        } catch (ParseException e) {
            e.printStackTrace();
            int f = e.getPosition();
        }

        return new IdContainer(UUID.fromString(""),
                UUID.fromString("responseUuid"));
    }
}
