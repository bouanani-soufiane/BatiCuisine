package org.example.mappers.dtomapper;

public interface EntityDtoMapper<E, R, S>  {

    E mapToEntity(R request);

    S mapToDto(E entity);
}
