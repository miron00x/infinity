package dao;

public interface Dao<Entity, Key> {
    Entity read(Key id) throws DaoException;

    Long create(Entity entity) throws DaoException;

    void update(Entity entity) throws DaoException;

    void delete(Key id) throws DaoException;
}
