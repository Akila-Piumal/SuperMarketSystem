package lk.ijse.pos.bo.Custom;

import lk.ijse.pos.bo.SuperBO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    boolean loginToSystem(String userName,String password) throws SQLException, ClassNotFoundException;
}
