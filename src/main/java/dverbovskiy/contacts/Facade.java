package dverbovskiy.contacts;

/**
 * Facade for all GET/POST/PUT/DELETE request
 * User: dverbovskiy
 */


import dverbovskiy.util.DebugUtil;
import dverbovskiy.web.rest.Container;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("contacts")
public class Facade {
    private static final Logger log = Logger.getLogger(Facade.class);

    @GET
    @Produces("text/html")
    public String get(@Context UriInfo ui) {
        log.debug(DebugUtil.START);
        MultivaluedMap<String, String> params = ui.getQueryParameters();

        if (params.containsKey("list")) {

        }
        log.debug(DebugUtil.END);
        return ("get");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(String input) {
        log.debug(DebugUtil.START);
        log.debug("RawInput:" + input);

        try {
            Container container = Container.getInstance(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug(DebugUtil.END);
        return Response.status(201).entity("").build();
    }

    @PUT
    @Produces("text/html")
    public String put() {
        log.debug(DebugUtil.START);
        log.debug(DebugUtil.END);
        return ("put");
    }

    @DELETE
    @Produces("text/html")
    public String delete() {
        log.debug(DebugUtil.START);
        log.debug(DebugUtil.END);
        return ("delete");
    }
}
