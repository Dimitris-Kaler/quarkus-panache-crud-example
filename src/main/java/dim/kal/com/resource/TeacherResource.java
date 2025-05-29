package dim.kal.com.resource;

import dim.kal.com.models.Teacher;
import dim.kal.com.services.ITeacherService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/teachers")
@Produces(MediaType.APPLICATION_JSON)
public class TeacherResource {

    ITeacherService service;

    @Inject
    public TeacherResource(ITeacherService service){
        this.service = service;
    }

    @GET
    public List<Teacher> getAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id")Long id){
       Teacher teacher=service.findById(id);

       return Response.ok(teacher).build();
    }

    @GET
    @Path("/name")
    public Response getByName(@QueryParam("name")String name){
        Teacher teacher = service.findByName(name);
        if (teacher == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(teacher).build();
    }

    @POST
    public Response create(Teacher teacher) {
        service.save(teacher);
        return Response.status(Response.Status.CREATED).entity(teacher).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Teacher teacher) {
        service.update(id, teacher);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }


}
