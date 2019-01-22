package dao;

import entities.Bill;

import java.util.List;

public interface BillDao extends Dao<Bill, Long> {
    List<Bill> readAll() throws DaoException;
}
