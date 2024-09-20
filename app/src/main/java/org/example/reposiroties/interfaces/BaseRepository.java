package org.example.reposiroties.interfaces;


import java.util.List;
import java.util.Optional;

public interface BaseRepository<Entity, ID> {

    List<Entity> findAll();

    Optional<Entity> findById(ID id);

    Optional<List<Entity>> findByColumn(String columnName, String value);

    Entity create(Entity entity);

    Entity update(ID id, Entity entity);

    boolean delete(ID id);

    boolean existsById(ID id);

    boolean existsByColumn(String columnName, String value);
}
