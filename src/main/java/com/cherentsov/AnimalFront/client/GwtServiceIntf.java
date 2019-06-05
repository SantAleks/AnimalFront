package com.cherentsov.AnimalFront.client;

import com.cherentsov.AnimalFront.shared.*;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import javax.ws.rs.*;
import java.util.List;

public interface GwtServiceIntf extends RestService {

    @GET
    @Path("getAll")
    public void gwtCallServer(final String sRequest, MethodCallback<List<Pet>> callback) throws IllegalArgumentException;

    @PUT
    @Path("getId")
    public void gwtCallServerTakeId(final Long lId, MethodCallback<List<Pet>> callback) throws IllegalArgumentException;

    @PUT
    @Path("search")
    public void gwtCallServerSearch(final String sRequest, MethodCallback<List<Pet>> callback) throws IllegalArgumentException;

    @PUT
    @Path("delete")
    public void gwtCallServerDelete(final Long lId, MethodCallback<Void> callback) throws IllegalArgumentException;
}
