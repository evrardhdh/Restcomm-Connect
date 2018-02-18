/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2014, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */
package org.restcomm.connect.http;

import static javax.ws.rs.core.MediaType.*;
import static javax.ws.rs.core.Response.*;
import static org.restcomm.connect.http.security.AccountPrincipal.SUPER_ADMIN_ROLE;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.restcomm.connect.commons.annotations.concurrency.ThreadSafe;
import org.restcomm.connect.commons.dao.Sid;

/**
 * @author quintana.thomas@gmail.com (Thomas Quintana)
 */
@Path("/Accounts/{accountSid}/Management/Gateways")
@ThreadSafe
@RolesAllowed(SUPER_ADMIN_ROLE)
public final class GatewaysXmlEndpoint extends GatewaysEndpoint {
    public GatewaysXmlEndpoint() {
        super();
    }

    private Response deleteGateway(final String accountSid, final String sid) {
        secure(super.accountsDao.getAccount(accountSid), "RestComm:Modify:Gateways");
        dao.removeGateway(new Sid(sid));
        return ok().build();
    }

    @Path("/{sid}.json")
    @DELETE
    public Response deleteGatewayAsJson(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid) {
        return deleteGateway(accountSid, sid);
    }

    @Path("/{sid}")
    @DELETE
    public Response deleteGatewayAsXml(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid) {
        return deleteGateway(accountSid, sid);
    }

    @Path("/{sid}.json")
    @GET
    public Response getGatewayAsJson(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid) {
        return getGateway(accountSid, sid, APPLICATION_JSON_TYPE);
    }

    @Path("/{sid}")
    @GET
    public Response getGatewayAsXml(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid) {
        return getGateway(accountSid, sid, retrieveMediaType());
    }

    @GET
    public Response getGatewaysList(@PathParam("accountSid") final String accountSid) {
        return getGateways(accountSid, retrieveMediaType());
    }

    @POST
    public Response createGateway(@PathParam("accountSid") final String accountSid, final MultivaluedMap<String, String> data) {
        return putGateway(accountSid, data, retrieveMediaType());
    }

    @Path("/{sid}.json")
    @POST
    public Response updateGatewayAsJsonPost(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid, final MultivaluedMap<String, String> data) {
        return updateGateway(accountSid, sid, data, APPLICATION_JSON_TYPE);
    }

    @Path("/{sid}.json")
    @PUT
    public Response updateGatewayAsJsonPut(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid, final MultivaluedMap<String, String> data) {
        return updateGateway(accountSid, sid, data, APPLICATION_JSON_TYPE);
    }

    @Path("/{sid}")
    @POST
    public Response updateGatewayAsXmlPost(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid, final MultivaluedMap<String, String> data) {
        return updateGateway(accountSid, sid, data, retrieveMediaType());
    }

    @Path("/{sid}")
    @PUT
    public Response updateGatewayAsXmlPut(@PathParam("accountSid") final String accountSid, @PathParam("sid") final String sid, final MultivaluedMap<String, String> data) {
        return updateGateway(accountSid, sid, data, retrieveMediaType());
    }
}
