package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.LoginDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        if (daoFactory==null){
            daoFactory=new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes{
        LOGIN
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types){
            case LOGIN:
                return new LoginDAOImpl();
            default:
                return null;
        }
    }
}
