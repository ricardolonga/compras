package br.com.ricardolonga.compras.application.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import br.com.ricardolonga.compras.domain.model.entities.Lista;
import br.com.ricardolonga.compras.domain.repositories.IListaRepository;

@Stateless
@Path("/listas")
public class ListaEndpoint {

    @Inject
    private IListaRepository listaRepository;

    @POST
    @Consumes("application/json")
    public Response create(Lista entity) {
        listaRepository.persist(entity);
        return Response.created(UriBuilder.fromResource(ListaEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") Long id) {
        Lista entity = listaRepository.findById(id);

        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        listaRepository.remove(entity);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces("application/json")
    public Response findById(@PathParam("id") Long id) {
        Lista entity = listaRepository.getListaCompletaById(id);

        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    public List<Lista> listAll() {
        return listaRepository.getListasCompletas();
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/json")
    public Response update(Lista entity) {
        listaRepository.persist(entity);
        return Response.noContent().build();
    }
}