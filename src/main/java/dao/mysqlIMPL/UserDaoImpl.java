package dao.mysqlIMPL;

import dao.DaoException;
import dao.UserDao;
import entities.Role;
import entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    @Override
    public List<User> readAll() throws DaoException {
        String sql = "SELECT `id`, `login`, `password`, `name`, `balance`, `role`, `status` FROM `users`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.values()[resultSet.getInt("role")]);
                user.setName(resultSet.getString("name"));
                user.setBalance(resultSet.getLong("balance"));
                user.setStatus(resultSet.getBoolean("status"));
                users.add(user);
            }
            return users;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public User readByLogin(String login) throws DaoException {
        String sql = "SELECT `id`, `password`, `name`, `balance`, `role`, `status` FROM `users` WHERE `login` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(login);
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.values()[resultSet.getInt("role")]);
                user.setName(resultSet.getString("name"));
                user.setBalance(resultSet.getLong("balance"));
                user.setStatus(resultSet.getBoolean("status"));
            }
            return user;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public User readByLoginAndPassword(String login, String password) throws DaoException {
        String sql = "SELECT `id`, `name`, `balance`, `role`, `status` FROM `users` WHERE `login` = ? AND `password` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(Role.values()[resultSet.getInt("role")]);
                user.setName(resultSet.getString("name"));
                user.setBalance(resultSet.getLong("balance"));
                user.setStatus(resultSet.getBoolean("status"));

            }
            return user;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    private Map<Long, User> usersCache = new HashMap<>();
    @Override
    public User read(Long id) throws DaoException {
        User user = usersCache.get(id);
        if(user == null) {
            String sql = "SELECT `login`, `password`, `name`, `balance`, `role`, `status` FROM `users` WHERE `id` = ?";
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = getConnection().prepareStatement(sql);
                statement.setLong(1, id);
                resultSet = statement.executeQuery();
                if(resultSet.next()){
                    user = new User();
                    user.setId(id);
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(Role.values()[resultSet.getInt("role")]);
                    user.setName(resultSet.getString("name"));
                    user.setBalance(resultSet.getLong("balance"));
                    user.setStatus(resultSet.getBoolean("status"));
                }
                usersCache.put(user.getId(), user);
                return user;
            } catch (SQLException e) {
                throw  new DaoException(e);
            } finally {
                try{ statement.close(); } catch(Exception e) {}
                try{ resultSet.close(); } catch(Exception e) {}
            }
        }
        return user;
    }

    @Override
    public Long create(User user) throws DaoException {
        String sql = "INSERT INTO `users` (`login`, `password`, `name`, `balance`, `role`, `status`) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setLong(4, user.getBalance());
            statement.setInt(5, user.getRole().ordinal());
            statement.setBoolean(6, user.isStatus());
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
    public void update(User user) throws DaoException {
        String sql = "UPDATE `users` SET `login` = ?, `password` = ?, `name` = ?, `balance` = ?, `status` = ?, `role` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setLong(4, user.getBalance());
            statement.setBoolean(5, user.isStatus());
            statement.setInt(6, user.getRole().ordinal());
            statement.setLong(7, user.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM `user` WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
        }
    }
}
