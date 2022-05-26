package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.SuperDAO;

import java.sql.SQLException;

public interface LoginDAO extends SuperDAO {
    boolean loginToSystem(String userName,String password) throws SQLException, ClassNotFoundException;
}
