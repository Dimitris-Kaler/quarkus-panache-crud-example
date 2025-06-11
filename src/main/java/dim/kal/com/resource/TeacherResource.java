package dim.kal.com.resource;

import dim.kal.com.dtos.TeacherDTO;
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
    public List<TeacherDTO> getAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id")Long id){
       TeacherDTO teacherDTO =service.findById(id);

       return Response.ok(teacherDTO).build();
    }

    @GET
    @Path("/name")
    public Response getByName(@QueryParam("name")String name){
        TeacherDTO teacherDTO = service.findByName(name);
        if (teacherDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(teacherDTO).build();
    }

    @POST
    public Response create(TeacherDTO teacherDTO) {
        service.save(teacherDTO);
        return Response.status(Response.Status.CREATED).entity(teacherDTO).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, TeacherDTO teacherDTO) {
        service.update(id, teacherDTO);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }


}
