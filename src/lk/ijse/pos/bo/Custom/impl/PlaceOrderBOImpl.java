package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.PlaceOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.OrderDAO;

import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }
}
