package repository.interfaces;

import java.util.List;

public interface CrudRepository<T> {

    T create(T entity);

    List<T> getAll();

    T getById(int id);

    boolean update(int id, T entity);

    boolean delete(int id);
}
