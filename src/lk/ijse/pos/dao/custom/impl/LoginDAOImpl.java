package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.LoginDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public boolean loginToSystem(String userName, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM log WHERE userName=? AND password=?", userName, password);
        if (resultSet.next()){
            return true;
        }
        return false;
    }
}
