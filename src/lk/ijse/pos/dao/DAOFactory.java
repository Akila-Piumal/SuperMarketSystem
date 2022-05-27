package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.LoginDAOImpl;
import lk.ijse.pos.dao.custom.impl.OrderDAOImpl;

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
        LOGIN,ORDER,CUSTOMER,ITEM,ORDERDETAIL
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types){
            case LOGIN:
                return new LoginDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            default:
                return null;
        }
    }
}
