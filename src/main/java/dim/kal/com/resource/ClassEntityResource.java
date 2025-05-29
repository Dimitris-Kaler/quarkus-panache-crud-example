package dim.kal.com.resource;



import dim.kal.com.models.ClassEntity;
import dim.kal.com.services.IClassEntityService;
import jakarta.inject.Inject;
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

@Path("/classes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClassEntityResource {
   IClassEntityService service;

   @Inject
   public ClassEntityResource(IClassEntityService service){
       this.service = service;

   }

   @GET
   public Response getAllClasses(){
       return Response.ok(service.findAllClasses()).build();
   }

    @GET
    @Path("/{id}")
    public Response getClassById(@PathParam("id")Long id){
        ClassEntity existing = service.findClassById(id);

       return existing != null?Response.ok(existing).build():Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/teacher/{teacherId}")
    public Response getByTeacher(@PathParam("teacherId") Long teacherId) {
        List<ClassEntity> existing = service.findByTeacherId(teacherId);
        return existing != null?Response.ok(existing).build():Response.status(Response.Status.NOT_FOUND).build();

    }

    @GET
    @Path("/title")
    public Response getByTitle(@QueryParam("title")String title){
       ClassEntity existing = service.findClassEntityByTitle(title);

       return existing !=null?Response.ok(existing).build():Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    public Response createClass(ClassEntity classEntity) {
        service.save(classEntity);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateClass(@PathParam("id") Long id, ClassEntity classEntity) {
        service.update(id, classEntity);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteClass(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
