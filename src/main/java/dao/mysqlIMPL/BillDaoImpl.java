package dao.mysqlIMPL;

import dao.BillDao;
import dao.DaoException;
import entities.Bill;
import entities.Service;
import entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl extends BaseDaoImpl implements BillDao{

    @Override
    public List<Bill> readAll() throws DaoException {
        String sql = "SELECT `id`, `user_id`, `service_id`, `bill`, `paid` FROM `bills`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            List<Bill> bills = new ArrayList<>();
            while(resultSet.next()) {
                Bill bill = new Bill();
                bill.setId(resultSet.getLong("id"));
                bill.setBill(resultSet.getLong("bill"));
                bill.setPaid(resultSet.getLong("paid"));
                Service service = new Service();
                service.setId(resultSet.getLong("service_id"));
                bill.setService(service);
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                bill.setUser(user);
                bills.add(bill);
            }
            return bills;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public Bill read(Long id) throws DaoException {
        String sql = "SELECT `user_id`, `service_id`, `bill`, `paid` FROM `bills` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            Bill bill = new Bill();
            if(resultSet.next()) {
                bill.setId(id);
                bill.setPaid(resultSet.getLong("paid"));
                bill.setBill(resultSet.getLong("bill"));
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                bill.setUser(user);
                Service service = new Service();
                service.setId(resultSet.getLong("service_id"));
                bill.setService(service);
            }
            return bill;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public Long create(Bill bill) throws DaoException {
        String sql = "INSERT INTO `bills` (`user_id`, `service_id`, `bill`, `paid`) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, bill.getUser().getId());
            statement.setLong(2, bill.getService().getId());
            statement.setLong(3, bill.getBill());
            statement.setLong(4, bill.getPaid());
            statement.executeUpdate();
            Long id = null;
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                id = resultSet.getLong(1);
            }
            return id;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public void update(Bill bill) throws DaoException {
        String sql = "UPDATE `bills` SET `user_id` = ?, `service_id` =?, `bill` = ?, `paid` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, bill.getUser().getId());
            statement.setLong(2, bill.getService().getId());
            statement.setLong(3, bill.getBill());
            statement.setLong(4, bill.getPaid());
            statement.setLong(5, bill.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM `bills` WHERE `id`=?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
        }
    }
}
