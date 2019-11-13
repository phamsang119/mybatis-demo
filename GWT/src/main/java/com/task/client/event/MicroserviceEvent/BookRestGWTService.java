package com.task.client.event.MicroserviceEvent;

import com.task.server.domain.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BookRestGWTService extends RestService {
    @GET
    @Path("/books")
    void list(MethodCallback<List<Book>> callback);

    @POST
    @Path("/books")
    void save(Book book, MethodCallback<Book> callback);

    @DELETE
    @Path("/books/{id}")
    void delete(@PathParam("id") Long id, MethodCallback<Book> callback);

    @GET
    @Path("/books/{id}")
    void getBook(@PathParam("id") Long id, MethodCallback<Book> callback);

}

