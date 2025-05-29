package dim.kal.com.resource;
import dim.kal.com.models.Student;
import dim.kal.com.services.IStudentService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    private IStudentService service;


    public StudentResource(IStudentService service){
        this.service =service;
    }

    @GET
    public Response getAll(){
        List<Student> students= service.getAllStudents();
        return Response.ok(students).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Student student = service.getStudentById(id);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(student).build();
    }

    @GET
    @Path("/email/{email}")
    public Response getByEmail(@PathParam("email") String email) {
        Student student = service.getStudentByEmail(email);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(student).build();
    }

    @GET
    @Path("/name")
    public Response getByName(@QueryParam("name") String name) {
        Student student = service.getStudentByName(name);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(student).build();
    }

    @POST
    public Response create(Student student) {
        service.createStudent(student);
        return Response.status(Response.Status.CREATED).entity(student).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Student updatedStudent) {
        service.updateStudent(id, updatedStudent);
        return Response.ok().build();
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.deleteStudent(id);
        return Response.noContent().build();
    }


}
