package dim.kal.com.services;

import dim.kal.com.dtos.ClassEntityDTO;
import dim.kal.com.mappers.ClassEntityMapper;
import dim.kal.com.models.ApiException;
import dim.kal.com.models.ClassEntity;
import dim.kal.com.repositories.IClassEntityRepository;
import dim.kal.com.validators.ClassEntityDTOValidator;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ClassEntityServiceSpec {
    @Mock
    IClassEntityRepository repository;

    @Mock
    ClassEntityMapper mapper;

    @Mock
    ClassEntityDTOValidator validator;

    @InjectMocks
    ClassEntityService service;

    @BeforeEach
    void setUp() {
        // Ενεργοποιεί τα @Mock και κάνει inject τα dependencies στο studentService
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get all classes - success")
    public void testGetAllClassess() {
        // Given
        ClassEntity classEntity1 = new ClassEntity();
        classEntity1.setTitle("Java");
        ClassEntity classEntity2 = new ClassEntity();
        classEntity2.setTitle("Javascript");

        ClassEntityDTO dto1 = new ClassEntityDTO();
        dto1.setTitle("Java");
        ClassEntityDTO dto2 = new ClassEntityDTO();
        dto2.setTitle("Javascript");

        when(repository.findAllClasses()).thenReturn(List.of(classEntity1, classEntity2));
        when(mapper.toDTO(classEntity1)).thenReturn(dto1);
        when(mapper.toDTO(classEntity2)).thenReturn(dto2);

        // When
        List<ClassEntityDTO> result = service.findAllClasses();

        // Then
        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getTitle());
        assertEquals("Javascript", result.get(1).getTitle());

        verify(repository, times(1)).findAllClasses();
        verify(mapper, times(2)).toDTO(any());
    }


    @Test
    @DisplayName("Find class by ID - success")
    public void testFindClassByIdSuccess() {
        ClassEntity classEntity = new ClassEntity();
        classEntity.id=1L;
        classEntity.setTitle("Java");

        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setId(1L);
        dto.setTitle("Java");

        when(repository.findClassById(1L)).thenReturn(classEntity);
        when(mapper.toDTO(classEntity)).thenReturn(dto);

        ClassEntityDTO result = service.findClassById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Java", result.getTitle());

        verify(repository).findClassById(1L);
        verify(mapper).toDTO(classEntity);
    }

    @Test
    @DisplayName("Find class by ID - not found")
    public void testFindClassByIdNotFound() {
        when(repository.findClassById(99L)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            service.findClassById(99L);
        });

        assertEquals("Class with ID 99 not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
    }

    @Test
    @DisplayName("Find class by title - success")
    public void testFindClassByTitleSuccess() {
        ClassEntity entity = new ClassEntity();
        entity.setTitle("Java");

        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setTitle("Java");

        when(repository.findByTitle("Java")).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);

        ClassEntityDTO result = service.findClassEntityByTitle("Java");

        assertEquals("Java", result.getTitle());

        verify(repository).findByTitle("Java");
        verify(mapper).toDTO(entity);
    }

    @Test
    @DisplayName("Find class by title - not found")
    public void testFindClassByTitleNotFound() {
        when(repository.findByTitle("NonExistent")).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            service.findClassEntityByTitle("NonExistent");
        });

        assertEquals("Class with name 'NonExistent' not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());
    }


    @Test
    @DisplayName("Find classes by teacher ID")
    public void testFindByTeacherId() {
        ClassEntity class1 = new ClassEntity();
        class1.setTitle("Math");
        ClassEntity class2 = new ClassEntity();
        class2.setTitle("Physics");

        ClassEntityDTO dto1 = new ClassEntityDTO();
        dto1.setTitle("Math");
        ClassEntityDTO dto2 = new ClassEntityDTO();
        dto2.setTitle("Physics");

        when(repository.findByTeacherId(10L)).thenReturn(List.of(class1, class2));
        when(mapper.toDTO(class1)).thenReturn(dto1);
        when(mapper.toDTO(class2)).thenReturn(dto2);

        List<ClassEntityDTO> result = service.findByTeacherId(10L);

        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).getTitle());
        assertEquals("Physics", result.get(1).getTitle());

        verify(repository).findByTeacherId(10L);
        verify(mapper, times(2)).toDTO(any());
    }


    @Test
    @DisplayName("Save class entity - success")
    public void testSaveClassEntity() {
        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setTitle("New Class");

        ClassEntity entity = new ClassEntity();
        entity.setTitle("New Class");

        doNothing().when(validator).validate(dto);
        when(mapper.toEntity(dto)).thenReturn(entity);
        doNothing().when(repository).save(entity);

        service.save(dto);

        verify(validator).validate(dto);
        verify(mapper).toEntity(dto);
        verify(repository).save(entity);
    }


    @Test
    @DisplayName("Update class entity - success")
    public void testUpdateSuccess() {
        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setTitle("Updated Class");

        ClassEntity existingEntity = new ClassEntity();
        existingEntity.id = 1L;
        existingEntity.setTitle("Old Class");

        ClassEntity updatedEntity = new ClassEntity();
        updatedEntity.setTitle("Updated Class");

        doNothing().when(validator).validate(dto);
        when(repository.findClassById(1L)).thenReturn(existingEntity);
        when(mapper.toEntity(dto)).thenReturn(updatedEntity);
        doNothing().when(repository).update(1L, updatedEntity);

        service.update(1L, dto);

        verify(validator).validate(dto);
        verify(repository).findClassById(1L);
        verify(mapper).toEntity(dto);
        verify(repository).update(1L, updatedEntity);
    }

    @Test
    @DisplayName("Update class entity - not found")
    public void testUpdateNotFound() {
        ClassEntityDTO dto = new ClassEntityDTO();
        dto.setTitle("Updated Class");

        doNothing().when(validator).validate(dto);
        when(repository.findClassById(99L)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            service.update(99L, dto);
        });

        assertEquals("Class with id '99' not found", exception.getMessage());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), exception.getStatusCode());

        verify(validator).validate(dto);
        verify(repository).findClassById(99L);
        verify(repository, never()).update(anyLong(), any());
    }



}
