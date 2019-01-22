package dao;

import entities.Service;

import java.util.List;

public interface ServiceDao extends Dao<Service, Long> {
    List<Service> readAll() throws DaoException;
}
