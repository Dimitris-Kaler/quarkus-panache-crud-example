package dim.kal.com.resource;
import dim.kal.com.dtos.MessageResponse;
import dim.kal.com.dtos.StudentDTO;
import dim.kal.com.models.Student;
import dim.kal.com.services.IStudentService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
import java.util.Map;


@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    private IStudentService service;

    @Inject
    public StudentResource(IStudentService service){
        this.service =service;
    }

    @GET
    public Response getAll(){
        List<StudentDTO> students= service.getAllStudents();
        return Response.ok(students).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        StudentDTO studentDTO = service.getStudentById(id);
//        if (studentDTO == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
        return Response.ok(studentDTO).build();
    }

    @GET
    @Path("/email/{email}")
    public Response getByEmail(@PathParam("email") String email) {
        StudentDTO studentDTO = service.getStudentByEmail(email);
//        if (studentDTO == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
        return Response.ok(studentDTO).build();
    }

    @GET
    @Path("/name")
    public Response getByName(@QueryParam("name") String name) {
        StudentDTO studentDTO = service.getStudentByName(name);
//        if (studentDTO == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
        return Response.ok(studentDTO).build();
    }

    @POST
    public Response create(StudentDTO studentDTO) {
        service.createStudent(studentDTO);
        return Response.status(Response.Status.CREATED).entity(studentDTO).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,StudentDTO updatedStudentDTO) {
        service.updateStudent(id, updatedStudentDTO);
        return Response.ok().build();
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.deleteStudent(id);
        return Response.noContent().build();
    }


}
