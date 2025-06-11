package dim.kal.com.mappers;

import dim.kal.com.dtos.ClassEntityDTO;

public interface IMapper<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
}
