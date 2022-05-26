package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.LoginBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.LoginDAO;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    private final LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public boolean loginToSystem(String userName, String password) throws SQLException, ClassNotFoundException {
        return loginDAO.loginToSystem(userName,password);
    }
}
